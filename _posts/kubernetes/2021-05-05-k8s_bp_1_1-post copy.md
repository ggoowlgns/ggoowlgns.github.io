---
title:      "Kubernetes Best Practices : 쿠버네티스 모범 사례 : Chapter 1"
subtitle:   "기본 서비스 설치"
date:       2021-05-05
author:     "Park Ji Hoon"
header-img: "img/post_k8s/k8s-logo.png"
header-mask: 0.3
catalog:    True
use_math: true
tags:
    - 책 리뷰
    - kubernetes
    - k8s

---
> source : https://github.com/brendandburns/kbp-sample
> * apiVersion issue :
>   * Deployment : extensions/v1beta1 -> apps/v1, request -> requests
>   * Ingress : extensions/v1beta1
> 
> [%] 의 표시 : 필자의 의견  
> kubernetes 에서 관리하는 Object 들(kind:[~])은 kubernetes Object로 표시

## Chapter 1. 기본 서비스 설치
#### 앱 개요
- redis 에 기록하는 journal service product
- component 종류
  - restful api (frontend)
  - file server (정적 파일 서버)
- ssl : 'let's encrypt' 
- 본 Chapter 에서 사용하는 방법
  - .yaml 선언
  - helm chart 사용


#### 설정 파일 관리
- 일반적인 선언적 방법 : .yaml 사용 `[%].yaml 은 indentation 에 민감하다.`
- 선언적 상태의 변경과 감시, 롤백을 이행 할 수 있어야 한다. : 보통 git으로 관리 (cluster에 적용되어 내용과 git으로 확인하는 내용이 일치해야 한다.) `[%] git과 cluster 를 동기화 시켜놓으면 git 관리를 통해서 rollback 도 가능 할 것 같다.`
- app을 파일 시스템으로 관리 할 때는 directory를 사용하여 component를 분리하여 관리하는게 좋다.
  - ```
    journal/
        frontend/
        redis/
        fileserver/
    ```

#### 이미지 관리 모범 사례
- 믿을 수 있는 공급자를 기반으로 하는 image 를 base로 해야한다. : 그렇지 않으면 보안에 취약하다 (조대협님 강의)
- 네이밍
  - 이미지가 빌드된 커밋의 SHA hash 와 결합하여 네이밍 하는것을 권장한다. ex) v1.0.1-bff02d

#### 실습 Product Application Diagram
![](/img/post_k8s/posting/chapter01/application-diagram.PNG)
## Product 배포 시작
#### Application Replica 생성
- Front End (API Server)
  - FE App 은 `Stateless` 이다.
  - State 는 Redis 에서 전적으로 관리한다.
    - 그렇기 때문에, traffic 에 영향을 주지 않고 임의로 `복제`가 가능하다.
  - App의 규모가 커질 가능성은 낮지만, `장애 대응`, `rollout 시에 downtime 방지`를 위해서 최소 두개의 replica를 실행하는 것이 좋다.
  - ```
    TIP : Replica를 관리하는 `ReplicaSet`이란 k8s Object 가 있지만, 이것 대신 `Deployment`를 사용하는 것을 권장한다. : 레플리카 복제 + 버전관리, 단계적 롤아웃 기능까지 지원한다.
    ```

 - frontend.yaml
   - 유의 사항 : pod를 찾을 수 있도록 label 을 설정해야함
   - resource 의 requests, limits : 최적의 효율을 위해 두 옵션을 차등 관리하면 더 좋지만, 보통 유저들은 `저조한 사용률 < 예측 가능성으로 부터 얻는 안정성` 을 택한다. (requests, limits 를 같이 둔다.)


#### CI/CD Setting 의 필요성
  - 책에서는 .yaml 의 파일을 수정/추가/삭제 시에는 바로바로 git 에 update 하여 version control 하도록 유도하고 있다. ([%]cmd 로 apply -f 전에 commit 하는 정도..: 물론 자동화 시키면 더 좋겠지?)
  - 프로젝트 초반부터 완벽한 CI/CD Pipeline 을 만드는 것이 부담스럽게 느껴질 수 있지만,
    - return : 이것이 주는 `신뢰성` 이라는 항목을 제쳐두고라도 `자동화`라는 항목을 통해 세이브 되는 시간만 생각하더라도 자동화 pipeline 구축은 매우 중요하다.
    - 또한 이미 명령적으로 배포된 app에 CI/CD Pipeline 을 다시 끼워넣는 것은 굉장히 어렵다.


#### Ingress 설정 (외부 Traffic 수용)
  - traffic 이 Cluster 내부의 Component 까지 들어오기 위해서는 두가지의 k8s Object 가 필요하다. ([%] 보편적인 구조를 가져가려면.. 물론 Service 로만 externalIP 를 노출시킬수 있다.)
    - 1. Service : L4 LB
      - 해당 예제에서는 ClusterIP Type 으로 사용하고 있다.
    - 2. Ingress : L7 LB
      - `Http Path` or `Host` 기반으로 Routing 이 가능하다.
      - 앞단에 Ingress 를 배치하면 향후 서비스 확장 측면에서 유연성을 확보할 수 있다.
        - 앞단의 Ingress 를 통해서 추가하는 Component로 Routing 만 해주면 되니까
        - [%] Ingress 의 routing rules
          - examples
            - ![](/img/post_k8s/posting/chapter01/ingress-example.PNG)
            - http://*/api ⇢ frontend:8080 (들어오는 host 를 설정 하지 않을 수도 있다. ('[ip address]/api' 로도 타고 들어올 수 가 있다.))
            - http://ingress.jhpark.testing.com/ ⇢ frontend:8080 (L7 LB 이니 만큼 host-domain 을 설정하는 것이 좋다. )
          - 해당 Ingress 에 붙는 IP (보통 VIP나 물리 LB가 될듯)는 각각 설정이 가능하다.
      

#### Appication Setting Using ConfigMap
  - [%] 앱 서비스에 관련된 설정들 ex) 한 페이지에 노출할 저널 항목 수, 특정 배경색, 공휴일 화면 등..
  - 보통 Application 과 Setting 은 분리하는게 좋다.
    - 동일한 App Binary 환경에 따라 달라지는 설정을 적용하고 싶을 때 활용 (spring 의 profile 처럼)
  - `configMap` 은 `K:V 형식으로 Value 에 '설정 정보'|'파일'`을 나타낸다.
  - ```cmd
	$ kubectl create configmap frontend-config --from-literal-journalEntries=10
	```
  
  - 적용한 configMap은 `환경 변수`를 이용하여 App에 설정 정보 적용
    - issue : configMap으로 설정을 관리한다고 했을 때 configMap 을 update 해도 pod는 재시작 하지 않는다. -> update 한 내용 적용x
    - 이를 해결하기 좋은 방법 : conigMap 의 네이밍에서 버전을 달아주는 것이다. : ex) frontend-config-v1
    - 이렇게 하면 Deployment 에 key 이름을 수정해야 할 것이고 -> 이는 Deployment 의 update 이므로 health checking 을 하면서 알아서 변경이 될 것이기 때문이다.
		-> 이전 버전으로의 롤백도 Deployment만 바꿔주면 되므로 app을 관리하기 수월하다.

#### Secret 인증 관리
  - config 와 거의 동일한 개념
  - 활용의 차이를 두기위해 존재하는 Object
  - 
	```cmd
	$ kubectl create secret generic redis-passwd --from-literal=passwd=${RANDOM}
	```
	- Secret Volume 을 Mount 하기 위해서는 두가지 설정이 필요하다.
  	- 1. Pod에 Volume을 추가하는 항목 : `containers`와 대등하게 들어간다.
  	- 2. 특정 conainer 에 Volume을 Mount 한다. : `containers`안에 들어간다.
	- 이렇게 하면 client service app 에서 redis secret 을 사용할 수 있게 된다.


#### Deploy `Stateful` DB
- State 가 존재하는 App을 배포하는 일은 개념적으로는 Stateless App 배포와 비슷하지만, 더 복잫ㅂ나다.
	- k8s에서는 `Node 상태`, `업그레이드`, `리밸런싱` 등의 이유로 pod가 reschedule 된다.
-> 이때, pod 가 다른 서버로 옮겨지면 redis instance 와 연관된 data가 특정 장비나 container 자체에 존재하는 경우 container 가 이관되거나 재시작 될 때, 해당 data 는 날라갈것이다. 
-> `PersistendVolume`을 그래서 사용한다.


```cmd
[아주 중요 - 배경 지식]
앞서 설명한 Secret Volume 과 마찬가지로 pod와 연계되어 있으며 `특정 위치의 container`에 마운트 된다.
하지만 PV는 일반적으로 `원격스토리지`에 저장이 된다. (NFS, SMB, iSCSI, 클라우드 기반 디스크 등 다양한 Network Protocol 을 통해서)
일반적으로 DB와 같은 App의 경우 `성능`이 더 좋은 `Block 기반 디스크`를 선호하지만
성능이 주요 고려 대상이 아니라면 `유연성`이 더 높은 `파일 디스크`를 더 선호한다.
```

#### StatefulSet으로 Redis 배포
- PVC로 `PV 리소스 요청` 하면 k8s 는 적절한 PV를 제공할 방법을 결정한다.
  - PVC 가 존재하는 이유는 두가지가 잇다.
    - 1. 디스크 명세가 다를 수 있다. : 여러 클라우드나 on-prem 사이에서 이식할 수 있도록 StatefulSet 작성이 가능하다.
    - 2. PV 는 오직 하나의 Pod에 Mount 될 수 있지만, PVC 를 통해 작성한 template 은 복제가 가능하다.
- [#] 만약 읽기 scale-out 이나 장애 탄력성을 위해서 redis-cluster 를 복제한다고 가정해보면
  - replica 1 -> 3
  - 두개의 새로운 replica 를 redis의 master에 연결해야 한다.
- redis statefulSet 서비스를 생성하면 dns (redis-0.redis)도 생성된다. : 이것은 첫번째 replica의 ip주소이다.
  -> 요걸 활용해서 모든 container에서 사용 가능한 간단한 스크립트를 만들어보자
	launch.sh
	```cmd
	#!/bin/sh
	PASSWORD=$(cat /etc/redis-passwd/passwd)

	if [[ "${HOSTNAME}" == "redis-0" ]]; then
		redis-server --requirepass ${PASSWORD}
	else
		redis-server --slaveof redis-0.redis 6379 --masterauth ${PASSWORD} --requirepass ${PASSWORD}
	fi
	```
	- 이 launch.sh를 file로 만들어서 configMap 으로 등록도 가능하다.
	- 	
		```cmd
		$ kubectl create configmap redis-config --from-file=./launch.sh
		```

	- Service 두개 생성
  	- redis read
  	- redis write (headless service : cluster ip 가 존재하지 x)
    	- 대신 statefulSet 안의 모든 pod에 대한 dns를 설정
## Product 배포 끝

#### Helm을 이용한 Product Applications 파라미터화
- 보통 같은 product 라도 다양한 환경에서 돌리게 된다.
  - dev, cp-dev, prod
- k8s cluster 의 version control이 필요한 상황이 된다. : `템플릿 시스템`을 사용한다.
  - 가장 대중적인게 바로 `Helm`
- helm 에서 product 는 `chart` 라는 파일 집합으로 패키징 된다.
- chart 는 자신의 Metadata를 정의한 chart.yaml 파일로 시작한다.
- 
	```yaml
	apiVersion: v1
	appVersion: "1.0"
	description: A Helm chart for our frontend journal server.
	name: frontend
	version: 0.1.0
	```
- 이 파일은 `chart dir` 의 최상위 (ex: frontend/)에 위치한다.
- 이 dir 안에는 `templates dir` 이 있습니다. 이 template 파일들은 `파라미터 참조`로 대체된다.
  - 
	```
	...
	spec:
		replicas: {{ .replicaCount }}
	...
	```
- `파라미터 값`은 values.yaml 파일에 정의된다.
- 모두 한곳에 둔 다음에, helm 도구를 이용하여 이 차트를 배포할 수 있다.
- ```
	helm install path/to/chart --values path/to/environment/values.yaml
	```
- 해당 예제의 file 구조
```cmd
journal/
	frontend/
		chart.yaml
		templates/
			frontend-deployment.yaml
			frontend-service.yaml
			frontend-configMap.yaml
			frontend-secret.yaml
			...
	...
values.yaml
```
- helm 정리 : Application 설정을 다양한 환경에서 재사용하려면 Application을 파라미터화 해야 합니다. 

#### 마치며 : 실습 K8S Cluster Setting Info with `LENS`
- Pod
  - ![](/img\post_k8s\posting\chapter01\pods-info.PNG)
- Service
  - ![](/img\post_k8s\posting\chapter01\services-info.PNG)
- ingresse
  - ![](/img\post_k8s\posting\chapter01\ingresses-info.PNG)


