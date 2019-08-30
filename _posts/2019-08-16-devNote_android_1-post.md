---
title:      "개발 노트 : Android - Kakao Map (작성중)"
subtitle:   "Developing with Kakao Map"
date:       2019-08-16
author:     "Park Ji Hoon"
header-img: "img/java_back.png"
header-mask: 0.3
catalog:    True
use_math: true
tags:
    - 개발 노트
    - Android


---
>

Fragment 생성시 자동 생성되는 onCreateView에서 view통해서 참조해서 findViewById 를 한다.

```java
-- 전략 --
private BottomSheetCoordinatorLayout bscl;
private View view;
-- 중략 --
@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_franchisee_map, container, false);
        bscl =  (BottomSheetCoordinatorLayout)view.findViewById(R.id.bscl);
    }
-- 후략 --
```

View



$End $ $of $ $Posting $


#### Reference
 - [[JAVA]이중루프, for문 빠져나가는 방법 - 손쉽게 코딩하기][1]


[1]:https://docu94.tistory.com/41
