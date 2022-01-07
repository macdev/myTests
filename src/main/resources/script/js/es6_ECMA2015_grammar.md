# ECMAScript2015 (ES6)

[블로그 참조](https://velog.io/@kimhscom/JavaScript-%EC%9E%90%EC%A3%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94-ES6-%EB%AC%B8%EB%B2%95-%EC%A0%95%EB%A6%AC)
, [콜백지옥](https://librewiki.net/wiki/%EC%BD%9C%EB%B0%B1_%EC%A7%80%EC%98%A5)

## const and let

const: 상수 선언

let: 범위내에서만 사용가능

## Arrow functions(화살표 함수)

기존의 방식:
```javascript
function (name) {
    return `name is ${name}`;
}
```

새로운 방식:
```javascript
(name) => { return `name is ${name}`; }
```

또는 return 도 생략 가능
```javascript
(name) => `name is ${name}`
```

## Template Literals(템플릿 리터럴)

``백틱(`)`` 을 사용하여 문자열 내 변수 사용 가능

```javascript
//이전
function myFunc1(name, age) {
	return '안녕' + name + '너의 나이는' + age + '살 이다!'; 
}

//ES6
const myFunc2 = (name, age) => {
	return `안녕 ${name}, 너의 나이는 ${age}살 이다!`; 
}
```


### Default parameters(기본 매개 변수)
parameter 에 기본값 지정 가능
```javascript
//이전
function myQuest1(year) {
	return '올해가 ' + year + '년 입니까?'; 
}
//log:올해가 undefined년 입니까? 

//ES6
const myQuest2 = (year = 2022) => {
	return `올해가 ${year}년 입니까?`; 
}
//log:올해가 2022년 입니까?
```

## Array and object destructing(배열 및 객체 비구조화)

```javascript
//데이터
var user = {
    fullName: "홍길동",
    age: 32
}

//기존
let fullName = user.fullName;
let name = user.fullName;
let age = user.age;
console.log(fullName);

//ES6
let {fullName, age} = user;
console.log(fullName);

//변수명이 모두 일치 해야 한다. 일치하지 않으면 undefined 할당된다.
//만약 변수명을 변경하고 싶다면 콜론(:)을 사용한다
let {fullName: name, age} = user;
console.log(name);
```
배열은 중괄호 대신 대괄호 사용

```javascript
const years = [2021, 2022, 2023, ...];
let [value1, value2, value3] = years;
console.log(value2); //log:2022
```

## Import and export(가져오기 및 내보내기)

다른 모듈에서 사용가능하도록 내보내기

```javascript
// ES6
export default function detail(name, age) {
	return `안녕 ${name}, 너의 나이는 ${age}살 이다!`;
}
```

사용하려는 곳에서 기능을 가져오기
```renderscript
import detail from './모듈파일명';

console.log(detail('영희', 20));
// 출력 => 안녕 영희, 너의 나이는 20살 이다!
```

둘 이상의 모듈 사용은 중괄호 이용.
```renderscript
import { detail, userProfile, getPosts } from './모듈파일명';
```

## Promises(프로미스)

### [참조1](https://sangminem.tistory.com/284), [참조2](https://sangminem.tistory.com/479)

비동기 코드를 쓰는 방법

```renderscript
const myPromise = () => {
	return new Promise((resolve, reject) => {
		resolve('안녕하세요 Promise가 성공적으로 실행했습니다');
	});
};

cosole.log(myPromise());
// Promise {<resolved>: "안녕하세요 Promise가 성공적으로 실행했습니다"}
```

| Promise의 3가지 상태 및 처리 흐름

- **pending(대기)** : 처리가 완료되지 않은 상태
- **fulfilled(이행)** : 성공적으로 처리가 완료된 상태
- **rejected(거부)** : 처리가 실패로 끝난 상태

### Promise 예시

```javascript
var prom1 = new Promise(function(resolve,reject){
    //business work
    if(work_result){
      resolve("success1");
    }
    else{
      reject("fail1");
    }
});
var prom2 = new Promise(function(resolve,reject){
    //business work
    if(work_result){
      resolve("success2");
    }
    else{
      reject("fail2");
    }
});

Promise.all([prom1, prom2]).then((result)=>{
    console.log(result);
});

// ["success1", "success2"]
```

#### 콜백이용

```renderscript
var ajax = function(option){
  setTimeout(function(){
    option.callback(option.data*2);
  }, 1000);
}

ajax({
  "url": "net1",
  "data": 2,
  "callback": function(v1){
    console.log(v1);

    ajax({
      "url": "net2",
      "data": v1,
      "callback": function(v2){
        console.log(v2);

        ajax({
          "url": "net3",
          "data": v2,
          "callback": function(v3){
            console.log(v3);
          }
        });
      }
    });
  }
})
```


#### 함수 분리

```renderscript
var ajax = function(option){
  setTimeout(function(){
    option.callback(option.data*2);
  }, 1000);
}

var cb1 = function(v1){
  console.log(v1);

  ajax({
    "url": "net2",
    "data": v1,
    "callback": cb2
  });
}
var cb2 = function(v2){
  console.log(v2);

  ajax({
    "url": "net3",
    "data": v2,
    "callback": cb3
  });
}
var cb3 = function(v3){
  console.log(v3);
}
ajax({
  "url": "net1",
  "data": 2,
  "callback": cb1
})
//의미가 있나 싶은.. 괜히 함수이름만 더 만드느라 피곤하기만 하고...분산되서 찾아 보기 힘들고..
```

#### Promise 사용

```renderscript
var prom1 = function(val){
  return new Promise((resolve, reject) => {
    setTimeout(()=>{
        try {
            resolve(val*2);
        } catch (err) {
            reject(err);
        }
    }, 1500);
  });
}

prom1(2)
  .then((val)=>{console.log(val); return prom1(val);})
  .catch((err) { console.error('에러 처리!!', err); })
  .then((val)=>{console.log(val); return prom1(val);})
  .then((val)=>{console.log(val); return prom1(val);});
  
```

### await / async (ECMAScript2017)

1. 함수앞에 async 를 붙이면 자동으로 일반 return 도 resolved promise 로 반환되어진다.
    - async를 선언한 함수는 항상 Promise 객체를 반환
2. async 함수 안에서만 사용할 수 있는 await 키워드
    - await 키워드가 처리 되는 동안 다른 프로세스가 중지되는것이아님.
    - async 내부에서는 await 이후 라인은 중지되는게 맞음(resolve, reject 기다림)
3. Promise.then 보다도 훨씬 간결하여 쓰기도 편하고 가독성도 뛰어남




## Rest parameter and Spread operator(나머지 매개 변수 및 확산 연산자)

Rest parameter는 배열의 인수를 가져오고 새 배열을 반환하는데 사용됩니다.

```
const arr = ['영희', 20, '열성적인 자바스크립트', '안녕', '지수', '어떻게 지내니?'];

// 비구조화를 이용한 값을 얻기
const [ val1, val2, val3, ...rest ] = arr;

const Func = (restOfArr) => {
	return restOfArr.filter((item) => {return item}).join(" ");
};

console.log(Func(rest)); // 안녕 지수 어떻게 지내니?
```
Spread operator는 Rest parameter와 구문이 동일하지만 Spread operator는 인수뿐만 아니라 배열 자체를 가집니다. for 반복문이나 다른 메서드를 사용하는 대신 Spread operator를 사용하여 배열의 값을 가져올 수 있습니다.

```
const arr = ['영희', 20, '열성적인 자바스크립트', '안녕', '지수', '어떻게 지내니?'];

const Func = (...anArray) => {
	return anArray;
};

console.log(Func(arr));
// 출력 => ["영희", 20, "열성적인 자바스크립트", "안녕", "지수", "어떻게 지내니?"]
```

배열, 객체 합치기로도 사용가능

```renderscript
let aa = ['a','b','c'];
let bb = ['d','e','f'];
console.log([...aa, ...bb]);
//['a', 'b', 'c', 'd', 'e', 'f']

let cc = {'a':1, 'b':2, 'c':3};
let dd = {'d':4, 'e':5, 'f':6};
console.log({...cc, ...dd});
/*
{
  "a": 1,
  "b": 2,
  "c": 3,
  "d": 4,
  "e": 5,
  "f": 6
}
*/
```


## Classes(클래스)
class는 객체 지향 프로그래밍(OOP)의 핵심입니다. 코드를 더욱 안전하게 캡슐화할 수 있습니다. class를 사용하면 코드 구조가 좋아지고 방향을 유지합니다.

class를 만들려면 class 키워드 뒤에 두 개의 중괄호가 있는 class 이름을 사용합니다.

```
class myClass {
	constructor() {
	
	}
}
```
이제 new 키워드를 사용하여 class 메서드와 속성에 액세스할 수 있습니다.
```
class myClass {
	constructor(name, age) {
		this.name = name;
		this.age = age;
	}
}

const user = new myClass('영희', 22);

console.log(user.name); // 영희
console.log(user.age); // 22
```
다른 class에서 상속하려면 extends 키워드 다음에 상속할 class의 이름을 사용합니다.
```
class myClass {
	constructor(name, age) {
		this.name = name;
		this.age = age;
	}

	sayHello() {
		console.log(`안녕 ${this.name} 너의 나이는 ${this.age}살이다`);
	}
}

// myClass 메서드 및 속성 상속
class UserProfile extends myClass {
	userName() {
		console.log(this.name);
	}
}

const profile = new UserProfile('영희', 22);

profile.sayHello(); // 안녕 영희 너의 나이는 22살이다.
profile.userName(); // 영희
```

