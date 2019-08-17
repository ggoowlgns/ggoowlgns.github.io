---
title:      "KIPS 학술발표대회 - Edge computing 기반 무인 마켓 사례 연구: 자원 분배 효율성 극대화"
subtitle:   "초안"
date:       2019-08-10
author:     "Park Ji Hoon"
header-img: "img/post_introducing_books/algorithm_1.jpg"
header-mask: 0.3
catalog:    true
use_math: true
tags:
    - KIPS
    - 논문
    - Edge computing
    - 무인 마켓
---
> 논문 초안

## <center>엣지 컴퓨팅 기반 무인 마켓 사례 연구: 자원 분배 효율성 극대화</center>

<!-- ##### <center>박지훈*, 류형오*, 김경률*, 김세화*<br> *한국외국어대학교 정보통신공학과 <br> e-mail:ggoowlgns@gmail.com </center> -->
<p style="color:black; font-size:100%;" align="center"> 박지훈*, 류형오*, 김경률*, 김세화*<br>
*한국외국어대학교 정보통신공학과 <br>
 e-mail:ggoowlgns@gmail.com</p>

<center>Edge Computing-Based Unmanned Market Case <br> Study: Maximizing Resource Distribution </center>
<center> Ji-Hoon Park*, Hyeong-Oh Ryu*, Kyung-Ryul Kim*, Saehwa Kim*<br> *Dept of Information Communications Engineering, Hankuk Universitiy of Foreign Studies </center>

<!--
최근 마켓 관련 기업들이 각기 다른 기술력을 지닌 무인 마켓들을 시장에 내놓고 있다. 그중에서 무인 마켓 이라는 이름
이 가장 걸맞는 Amazon사의 Amazon Go를 벤치마킹 하였다. 이때 요구되는 기술인 Object Tracking, Object Detection은
막대한 처리량을 요구하여 각 카메라 디바이스에서 처리하기에는 비용적인 문제가 있고, Cloud Computing을 활용하여 Cloud
에서 처리 하기에는 여러대의 카메라에서 실시간으로 영상 정보(frame)를 전송할때 발생하는 교통 혼잡(traffic congestion)
문제가 발생한다. 본 논문에서는 Edge Computing 을 활용하여 Edge에서 로컬 네트워크상에 있는 각 디바이스로 부터 데이터
를 수집하여 처리하는 서비스를 제안한다. 각 디바이스에서는 실시간 영상 정보, 센서 정보 등을 Edge로 전송하여 Edge에서
로컬로 처리한다. 고객들의 영상 정보는 모두 Local Edge에서 처리되어 고객들의 프라이버시를 지켜주고, Edge의 네트워크가
일시적으로 끊겨도 고객들에게 서비스를 지속할 수 있도록 구축하여 기존의 방식보다 양질의 서비스를 제공 할수 있다. 본
논문에서는 제안한 서비스를 이용하여 고객이 서비스 받았을 때와 Cloud에서 처리 했을 때 성능을 비교하였다. 성능 비교
결과 Edge Computing 을 활용한 서비스가 높은 응답(서비스) 속도를 보여주었다 -->

###### <center>요  약</center>
<center>
Amazon Go와 같은 완전한 무인 마켓은 대용량의 이미지 처리 프로세스와 소모 대역폭이 요구된다. 본 논문에서는 엣지 컴퓨팅을 무인마켓에 도입하여 각 말단 디바이스로 부터 실시간 영상 정보, 센서 정보 등을 근처 엣지 디바이스에서 수집하여 처리한다. 또한 수집하는 과정에서 사용되는 실시간 영상 스트리밍시 로컬 네트워크의 효율적인 대역폭 할당을 위해 다중 스트림에 적합한 대역폭 할당 기법을 제안한다. 카메라 단위와 매대 단위로 구분해서, 각 카메라 별로는 모션 인식기술을 활용하여 영상의 해상도를 평소에는 저해상도로 송신하고 프레임 변화가 감지되면 각 이미지 프로세스에서 요구되는 해상도로 조절하였다. 매대 단위로는 네트워크에서 수용가능한 대역폭 임계치에 도달하게되면 매대별 우선순위에 따른 대역폭 할당 스케줄링 기법을 구현하였다. (모든 처리는 엣지 디바이스에서 명령 하달에 의해 처리된다.) 이를 통해 엣지 컴퓨팅 사용시에 향상된 응답 시간을 보여주고, ( 효율적인 네트워크 활용을 통해 가용가능한 매대수의 증감을 보였다 ) or (peak rate 감소를 통한 다중 송신 이득의 향상을 보였다)(?).
<!-- 결과 : 소모 대역폭 감소, peak rate 감소를 통한 다중 송신 이득의 향상
목표 : 매대수 상승?
효율적? - 무인 마켓의 과부화로 인한 오작동 방지 -->

</center>



##### 1. 서론
##### 2. 엣지 컴퓨팅 기반 무인 마켓 아키텍쳐
##### 3. 엣지 컴퓨팅 효율화를 위한 자원 분배 기법
##### 4. 실험
###### 4.1. 실험환경
###### 4.2. 실험결과
##### 5. 결 론
