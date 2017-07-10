# DMXArio

<a href='https://play.google.com/store/apps/details?id=com.icaynia.dmxario'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height=90px/></a>


[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/LEnUN-5gwjY/0.jpg)](https://www.youtube.com/watch?v=LEnUN-5gwjY)
https://www.youtube.com/watch?v=LEnUN-5gwjY

## 소개
DMXArio는 무대 조명 제어용 어플리케이션입니다.

## 기능
- Seekbar로 각 채널마다 값 전송을 확인해 볼 수 있습니다. 기기 별 동시 전송도 가능합니다.
- Position 기능으로 조명의 위치, 색 등의 상태를 저장시킬 수 있으며 최대 48개의 Position을 지원합니다. 원터치로 조명 상태를 변경할 수 있습니다.
- Scene 기능으로 조명 상태 제어를 녹화할 수 있습니다. 원터치로 녹화된 움직임을 재생할 수 있습니다.

## 사용 방법
어플리케이션을 사용하기 전에 하드웨어의 설치가 먼저 필요합니다.

### 하드웨어 설치 단계
- 먼저 무대 조명을 설치한 후 각 조명 장치마다 index 채널을 1, 17, 33, 49로 설정합니다. (4개 이하인 경우도 가능)
- 아두이노와 DMX전용 블루투스 쉴드를 결합한 후 전원을 공급합니다.

### 블루투스 설정
- 먼저 안드로이드 기기와 아두이노와의 페어링이 필요합니다. 환경 설정에서 페어링을 해 주세요.
- 어플리케이션 실행 후 'Setting'에 들어간 후 전 단계에서 페어링 한 아두이노를 선택합니다.

### [참조] 신호 전달
1. 안드로이드 기기에서 블루투스를 통해 신호 전송 (HC-06)
2. 아두이노에서 신호 파싱(Process)
3. DMX 신호 출력(MAX485) 



### Developer:
[icaynia](https://github.com/icaynia)

### Dependencies:

   * Arduino, HC-06 Bluetooth Module, MAX485 for DMX signal transmitting
   * Android Studio 2.2 or later.
   * Build tools version : 24.0.0

