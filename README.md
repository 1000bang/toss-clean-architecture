# Toss Clean Architecture — Package, Layer, Module

> 토스 SLASH 22 컨퍼런스에서 김재민(Gemini Kim) 님이 발표한
> **"지속 성장 가능한 코드를 만들어가는 방법"** 을 기반으로 구성한 프로젝트입니다.

## 한줄 요약

> 토스페이먼츠는 **Package(개념 응집) - Layer(단방향 참조) - Module(기술 격리)** 세 가지 축으로 코드를 통제하여, 처음부터 완벽하지 않더라도 지속적으로 성장 가능한 아키텍처를 만든다.

---

## 프로젝트 구조

```
core/
├── controller/              ← Presentation Layer
│   ├── card/
│   │   ├── CardPaymentController.java
│   │   ├── CardPaymentHttpRequest.java
│   │   └── CardPaymentHttpResponse.java
│   └── mobile/
│       ├── MobilePaymentController.java
│       ├── MobilePaymentHttpRequest.java
│       └── MobilePaymentHttpResponse.java
│
├── domain/                  ← Business Layer
│   ├── card/                ← 개념 단위 응집 (Package 원칙)
│   │   ├── Card.java
│   │   ├── CustomerCard.java
│   │   ├── CardPaymentRequest.java
│   │   ├── CardPaymentResult.java
│   │   ├── CardValidator.java
│   │   ├── CardPaymentProcessor.java
│   │   ├── CardReader.java          (인터페이스)
│   │   └── CardService.java
│   ├── mobile/              ← Layer 원칙 시연
│   │   ├── MobilePaymentRequest.java
│   │   ├── MobilePaymentResult.java
│   │   └── MobileService.java
│   ├── store/
│   │   ├── Store.java
│   │   └── StoreReader.java          (인터페이스)
│   └── combine/             ← Module 원칙 시연
│       ├── CacheClient.java           (인터페이스)
│       ├── ExternalApiClient.java     (인터페이스)
│       ├── CombineTarget.java
│       └── CombineService.java
│
└── infrastructure/          ← 기술 구현체 격리
    ├── cache/
    │   └── InMemoryCacheClient.java
    ├── http/
    │   └── RestTemplateExternalApiClient.java
    └── persistence/
        ├── JpaCardReader.java
        └── JpaStoreReader.java
```

---

## 1. Package — "역할이 아닌, 개념 단위로 뭉쳐라"

### 배경/문제

보통 Spring 프로젝트를 만들면 **역할별로** 패키지를 나눕니다:

```
core/
├── component/     ← CardPaymentProcessor, CardValidator
├── dataaccess/    ← StoreReader, CardReader
├── service/       ← CardService
└── vo/            ← Card, CustomerCard, CardPaymentRequest
```

이렇게 하면 `CardService`의 import가 사방팔방으로 흩어집니다:

```java
// 잘못된 예 - import가 4개의 서로 다른 패키지를 참조
import com.tosspayments.payments.core.component.CardPaymentProcessor;
import com.tosspayments.payments.core.component.CardValidator;
import com.tosspayments.payments.core.dataaccess.CardReader;
import com.tosspayments.payments.core.dataaccess.StoreReader;
import com.tosspayments.payments.core.vo.Card;
import com.tosspayments.payments.core.vo.CardPaymentRequest;
import com.tosspayments.payments.core.vo.CustomerCard;
```

**문제**: "카드"라는 하나의 개념을 수정하려면 `component`, `dataaccess`, `vo`, `service` 4개 패키지를 모두 건드려야 합니다.

### 토스의 접근 방식

"카드"라는 **개념에 관련된 모든 것**을 하나의 패키지에 응집시킵니다:

```
core/domain/
├── card/          ← 카드에 관한 모든 것이 여기에
│   ├── Card.java
│   ├── CustomerCard.java
│   ├── CardPaymentRequest.java
│   ├── CardValidator.java
│   ├── CardPaymentProcessor.java
│   ├── CardReader.java
│   └── CardService.java
```

### 프로젝트에서 확인 — `CardService.java`

```java
package com.tosspayments.payments.core.domain.card;

// 같은 패키지이므로 Card, CardValidator, CardReader 등은 import 불필요!

import com.tosspayments.payments.core.domain.store.StoreReader;  // 외부 개념만 import
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CardValidator cardValidator;                // 같은 패키지
    private final CardReader cardReader;                      // 같은 패키지
    private final CardPaymentProcessor cardPaymentProcessor;  // 같은 패키지
    private final StoreReader storeReader;                    // 다른 개념 → 명시적 import
}
```

### 얻을 수 있는 인사이트

- **import 문이 곧 아키텍처 다이어그램이다**
- 같은 개념은 import가 아예 없거나 최소 → 높은 응집도
- 다른 개념(`store`)만 명시적 import → 의존 관계가 한눈에 보임

---

## 2. Layer — "위에서 아래로만, 절대 거슬러 올라가지 마라"

### 3가지 규칙

| 규칙 | 설명 |
|---|---|
| **순방향만 참조** | Presentation → Business → Persistence |
| **역류 금지** | Business가 Presentation을 참조하면 안 됨 |
| **건너뛰기 금지** | Presentation이 Persistence를 직접 참조하면 안 됨 |

### 잘못된 예시 — Business Layer가 Presentation을 역류 참조

```java
package com.tosspayments.payments.core.domain.mobile;

// Business Layer가 Presentation Layer로 접근! (역류!)
import com.tosspayments.payments.core.controller.MobilePaymentHttpRequest;   // ← 역류!
import com.tosspayments.payments.core.controller.MobilePaymentHttpResponse;  // ← 역류!

@Service
public class MobileService {
    public MobilePaymentHttpResponse addPayment(MobilePaymentHttpRequest request) { ... }
}
```

### 올바른 예시 — Controller가 변환 책임을 진다

**`MobilePaymentController.java`** (Presentation Layer):

```java
@RestController
public class MobilePaymentController {

    @PostMapping("/payments/mobile")
    public MobilePaymentHttpResponse addMobilePayment(@RequestBody MobilePaymentHttpRequest request) {
        // [핵심 1] HTTP DTO → 도메인 객체로 변환
        MobilePaymentRequest domainRequest = new MobilePaymentRequest(
                request.getPhoneNumber(),
                request.getCarrier(),
                request.getAmount()
        );

        MobilePaymentResult result = mobileService.addPayment(domainRequest);

        // [핵심 2] 도메인 결과 → HTTP 응답으로 변환
        return new MobilePaymentHttpResponse(result.getPaymentId(), result.getStatus());
    }
}
```

**`MobileService.java`** (Business Layer):

```java
package com.tosspayments.payments.core.domain.mobile;

// controller 패키지에 대한 import가 전혀 없다!

@Service
public class MobileService {
    public MobilePaymentResult addPayment(MobilePaymentRequest request) {
        String paymentId = "MOBILE-" + System.currentTimeMillis();
        return new MobilePaymentResult(paymentId, "SUCCESS");
    }
}
```

### 참조 흐름

```
[Presentation Layer]                    [Business Layer]
MobilePaymentController  ──────────→   MobileService
        │                                    │
  HttpRequest DTO                     MobilePaymentRequest  (도메인)
  HttpResponse DTO                    MobilePaymentResult   (도메인)
        │                                    │
        └── 변환 책임은 Controller에서 ──────┘
```

### 얻을 수 있는 인사이트

- **변환의 책임**은 상위 레이어(Controller)가 진다
- Business Layer의 import에 `controller` 패키지가 보이면 **즉시 아키텍처 위반**
- HTTP 스펙이 바뀌어도 `MobileService`는 **한 줄도 수정할 필요 없음**

---

## 3. Module — "비즈니스 로직에서 기술을 몰아내라"

### 잘못된 예시 — 비즈니스 코드에 기술이 침투

```java
package com.tosspayments.payments.core.domain.combine;

import com.amazonaws.cache.Cache;              // ← AWS SDK 직접 의존!
import com.amazonaws.cache.KeyConverter;       // ← AWS SDK 직접 의존!
import feign.Feign;                            // ← Feign 직접 의존!
import feign.httpclient.ApacheHttpClient;      // ← Feign 직접 의존!

@Service
public class ExampleCombineService {
    public void combine(CombineTarget target) { ... }
}
```

**문제**: AWS SDK 메이저 업데이트나 Feign → WebClient 교체 시 **비즈니스 로직까지 수정** 필요

### 올바른 예시 — 인터페이스로 기술 격리

**도메인이 인터페이스를 소유** (`domain/combine/`):

```java
public interface CacheClient {
    void put(String key, String value);
    String get(String key);
}

public interface ExternalApiClient {
    String call(String endpoint, String payload);
}
```

**비즈니스 로직은 인터페이스만 사용** (`CombineService.java`):

```java
// 외부 라이브러리 import가 전혀 없다!

@Service
public class CombineService {
    private final CacheClient cacheClient;
    private final ExternalApiClient externalApiClient;

    public void combine(CombineTarget target) {
        String cached = cacheClient.get(target.getTargetId());
        if (cached == null) {
            String result = externalApiClient.call("/combine", target.getData());
            cacheClient.put(target.getTargetId(), result);
        }
    }
}
```

**기술 구현체는 `infrastructure`에 격리** (`InMemoryCacheClient.java`):

```java
package com.tosspayments.payments.core.infrastructure.cache;

// 외부 라이브러리는 여기서만 import
@Component
public class InMemoryCacheClient implements CacheClient {
    // AWS → Redis로 바꾸고 싶으면? 이 파일만 수정!
    // CombineService는 변경 없음!
}
```

### 격리 구조

```
[domain - 비즈니스 로직]                [infrastructure - 기술 구현체]

CombineService
   │
   ├── CacheClient (인터페이스) ◄────── InMemoryCacheClient
   │                                    (→ AWS, Redis, Caffeine 교체 자유)
   │
   └── ExternalApiClient (인터페이스) ◄── RestTemplateExternalApiClient
                                        (→ Feign, WebClient 교체 자유)
```

### 얻을 수 있는 인사이트

- 비즈니스 코드에 `com.amazonaws`, `feign`, `redis` 같은 import가 보이면 **기술 침투**
- 인터페이스는 **도메인이 소유**하고, 구현체는 **infrastructure가 소유**
- 기술 교체 시 `infrastructure` 패키지만 수정 → 비즈니스 로직 **무변경**

---

## 인상적인 문장들

> *"import문을 통해 의존성의 방향, 응집도, 레이어 위반 여부를 즉시 파악할 수 있다."*

> *"import 한 줄까지도 소중히 여기며 관리해야 한다. 영원히 완벽한 코드와 설계는 존재하지 않는다. 소프트웨어는 생명체처럼 끊임없이 진화한다."*

> *"지속 가능한 소프트웨어는 지속 가능한 코드 기반에서 나온다."*

---

## 3가지 원칙 요약

| 원칙 | 잘못된 예 | 올바른 예 |
|---|---|---|
| **Package** | `component/`, `dataaccess/`, `vo/`로 역할별 분리 | `card/` 패키지에 모두 응집 |
| **Layer** | Service가 Controller의 HttpRequest를 import (역류) | Controller가 DTO→도메인 객체 변환 후 전달 |
| **Module** | Service가 AWS, Feign 라이브러리 직접 import | 인터페이스만 사용, 구현은 infrastructure에 격리 |

---

## 참고

- **발표**: [토스 SLASH 22 — 지속 성장 가능한 코드를 만들어가는 방법](https://youtu.be/RVO02Z1dLF8)
- **발표자**: 김재민(Gemini Kim), 토스페이먼츠 서버 개발자
- **키워드**: `#토스페이먼츠` `#SLASH22` `#클린아키텍처` `#헥사고날아키텍처` `#도메인주도설계` `#의존성역전`
