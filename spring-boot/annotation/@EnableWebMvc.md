## @EnableWebMvc

`@EnableWebMvc`는 SpringMVC를 구성할 때 필요한 Bean 설정들을 자동으로 해주는 어노테이션이다.

또한, 기본적으로 등록되는 Bean들 이외에 추가적으로 개발자가 필요로하는 Bean 등록을 손쉽게 할 수 있도록 도와준다.

Spring boot의 경우 `implementation 'org.springframework.boot:spring-boot-starter-web`만 추가하면 mvc와 관련된 다양한 bean 설정을 대신 해준다.

<br>

```yaml
spring:
  jackson:
    property-naming-strategy: SNAKE_CASE
```

아무리 yaml에서 SNAKE_CASE로 설정을 해줘도 응답이 CamelCase로 왔다.

`@EnableWebMvc`를 Configuration에 붙이게 되면 Spring boot의 기본적인 웹 MVC 기능들을 제외시키기 때문에 yaml에서 설정이 적용되지 않았던 것이다.

DTO마다 `@JsonNaming` 어노테이션을 사용하면 적용되지만, 같은 내용이 중복되는 것보다 전역으로 한 번만 설정해주는 것이 더 맞다고 생각한다.

### 해결

Configuration에 있던 `@EnableWebMvc`를 지우면 된다.

> I had `@EnableWebMvc` annotation in one of the classes (ExceptionHandler) in my Application (face-palm!).
>
> But, as per this [issue](https://github.com/spring-projects/spring-boot/issues/2566),
>
> > If you have @EnableWebMvc annotation, that disables the auto-configuration of Spring MVC, including the configuring of its message converters to customize Jackson's serialization.
> >
> > It's expected behavior when you use @EnableWebMvc as, by doing so, you're telling Spring Boot that you want to take control of Spring MVC's configuration. That includes configuring its HTTP message converters to (de)serialize JSON in the manner that meets your needs.
> >
> > If you want to override the Jackson configuration, you can either use the spring.jackson.* properties or, if you want more control, declare your own Jackson2ObjectMapperBuilder bean.
>
> Once I removed `@EnableWebMvc` annotation, this property works as expected.
>
> https://stackoverflow.com/questions/40649177/jackson-is-ignoring-spring-jackson-properties-in-my-spring-boot-application