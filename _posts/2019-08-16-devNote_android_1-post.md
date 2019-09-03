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
> [git private repo](https://github.com/ggoowlgns/android_kakao_map_example)  
> [https://github.com/ggoowlgns/android_kakao_map_example](https://github.com/ggoowlgns/android_kakao_map_example)  
> 소스를 원하시는 분은 댓글 달아주세요!

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

###### 실행 화면
![version 1.0](/img/post_android/test_1.gif)

$End $ $of $ $Posting $


#### Reference
 - [[JAVA]이중루프, for문 빠져나가는 방법 - 손쉽게 코딩하기][1]


[1]:https://docu94.tistory.com/41
