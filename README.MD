* dagger2 관련파일
 - kakao/GlovalApplication.java <- 어플리케이션 자체를 데거앱컴포넌트에 주입시킴
 - package// dagger/Appcomponent.java <- 데거 컴포넌트의 시작점
                                         (필수 모듈 이외 아래 2가지 모듈을 추가적으로 참조한다

 - package// dagger/AcitibityModule   <- 데거에 관리는 엑티비티를 반환해주는 모듈
 - package// dagger/AppModule.java <- 데거에 관리되는 어플리케이션을 반환해주는 모듈
