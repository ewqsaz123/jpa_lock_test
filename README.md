# jpa_lock_test
jpa lock 테스트 코드

# DB 설정
dir : src/main/resources/application.properties
<img width="807" alt="image" src="https://user-images.githubusercontent.com/20436113/203799462-82542de6-e707-4833-8786-72ff608d2bff.png">


# 테스트 실행
dir : src/test/java/com/sample/simpleproject/LockTest.java

  - 낙관적 락_NONE
  <img width="839" alt="image" src="https://user-images.githubusercontent.com/20436113/203800001-d27c592b-9d16-48e9-8a46-ccd9de8560da.png">


  - 낙관적 락_OPTIMISTIC
  <img width="1249" alt="image" src="https://user-images.githubusercontent.com/20436113/203800125-7ae55ae6-5b54-4c48-b612-d0903fdbebff.png">


  - 비관적 락_PESSIMISTIC
  <img width="779" alt="image" src="https://user-images.githubusercontent.com/20436113/203800221-ff36024e-6ef1-4baa-9082-597296c27d9c.png">


  - 비관적 락_PESSIMISTIC_FORCE_INCREMENT
  <img width="779" alt="image" src="https://user-images.githubusercontent.com/20436113/203800289-7e19dbfd-d634-4fb1-ad6e-20eb0565498c.png">
