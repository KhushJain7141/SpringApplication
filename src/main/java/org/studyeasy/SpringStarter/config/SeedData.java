package org.studyeasy.SpringStarter.config;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.studyeasy.SpringStarter.models.Account;
import org.studyeasy.SpringStarter.models.Authority;
import org.studyeasy.SpringStarter.models.Post;
import org.studyeasy.SpringStarter.services.AccountService;
import org.studyeasy.SpringStarter.services.AuthorityService;
import org.studyeasy.SpringStarter.services.PostService;
import org.studyeasy.SpringStarter.util.constants.Privillages;
import org.studyeasy.SpringStarter.util.constants.Roles;
@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthorityService authorityService;
    @Override
    public void run(String... args) throws Exception {
        for(Privillages auth: Privillages.values()){
            Authority authority = new Authority();
            authority.setId(auth.getId());
            authority.setName(auth.getPrivillage());
            authorityService.save(authority);
        }
        Account account01 = new Account();
        Account account02 = new Account();
        Account account03 = new Account();
        Account account04 = new Account();
        account01.setEmail("sample@gmail.com");
        account01.setPassword("password");
        account01.setFirstname("user");
        account01.setLastname("lastname");
        account01.setAge(25);
        account01.setDate_of_birth(LocalDate.parse("1990-01-01"));
        account01.setGender("Male");
        account02.setEmail("shreedeelipent@gmail.com");
        account02.setPassword("password");
        account02.setFirstname("Admin");
        account02.setLastname("lastname");
        account02.setRole(Roles.ADMIN.getRole());
        account02.setAge(23);
        account02.setDate_of_birth(LocalDate.parse("1990-01-01"));
        account02.setGender("Female");
        account03.setEmail("editort@editor.com");
        account03.setFirstname("Editor");
        account03.setPassword("password");
        account03.setLastname("lastname");
        account03.setRole(Roles.EDITOR.getRole());
        account03.setAge(26);
        account03.setDate_of_birth(LocalDate.parse("1990-01-01"));
        account03.setGender("Male");
        account04.setEmail("super_editor@editor.com");
        account04.setPassword("password");
        account04.setFirstname("SuperEditor");
        account04.setLastname("lastname");
        account04.setAge(40);
        account04.setDate_of_birth(LocalDate.parse("1980-01-01"));
        account04.setGender("Female");
        account04.setRole(Roles.EDITOR.getRole());
        Set<Authority> authorities = new HashSet<>();
        authorityService.findById(Privillages.RESENT_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
        authorityService.findById(Privillages.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
       
        account04.setAuthorities(authorities);


        
        accountService.save(account01);
        accountService.save(account02);
        accountService.save(account03);
        accountService.save(account04);
        List<Post> posts = postService.getAll();
        if(posts.size()==0)
        {
            Post post01 = new Post();
            post01.setTitle("Post 01");
            post01.setBody("Post 01 body...............");
            post01.setAccount(account01);
            postService.save(post01);


            Post post02 = new Post();
            post02.setTitle("Post 02");
            post02.setBody("Post 02 body...............");
            post02.setAccount(account02);
            postService.save(post02);

            Post post03 = new Post();
            post03.setTitle("Post 02");
            post03.setBody("17. Web MVC framework\r\n" + //
                                "17.1 Introduction to Spring Web MVC framework\r\n" + //
                                "The Spring Web model-view-controller (MVC) framework is designed around a DispatcherServlet that dispatches requests to handlers, with configurable handler mappings, view resolution, locale and theme resolution as well as support for uploading files. The default handler is based on the @Controller and @RequestMapping annotations, offering a wide range of flexible handling methods. With the introduction of Spring 3.0, the @Controller mechanism also allows you to create RESTful Web sites and applications, through the @PathVariable annotation and other features.\r\n" + //
                                "\r\n" + //
                                "“Open for extension...”\r\n" + //
                                "\r\n" + //
                                "A key design principle in Spring Web MVC and in Spring in general is the “Open for extension, closed for modification” principle.\r\n" + //
                                "\r\n" + //
                                "Some methods in the core classes of Spring Web MVC are marked final. As a developer you cannot override these methods to supply your own behavior. This has not been done arbitrarily, but specifically with this principle in mind.\r\n" + //
                                "\r\n" + //
                                "For an explanation of this principle, refer to Expert Spring Web MVC and Web Flow by Seth Ladd and others; specifically see the section \"A Look At Design,\" on page 117 of the first edition. Alternatively, see\r\n" + //
                                "\r\n" + //
                                "Bob Martin, The Open-Closed Principle (PDF)\r\n" + //
                                "\r\n" + //
                                "You cannot add advice to final methods when you use Spring MVC. For example, you cannot add advice to the AbstractController.setSynchronizeOnSession() method. Refer to Section 9.6.1, “Understanding AOP proxies” for more information on AOP proxies and why you cannot add advice to final methods.\r\n" + //
                                "\r\n" + //
                                "In Spring Web MVC you can use any object as a command or form-backing object; you do not need to implement a framework-specific interface or base class. Spring's data binding is highly flexible: for example, it treats type mismatches as validation errors that can be evaluated by the application, not as system errors. Thus you need not duplicate your business objects' properties as simple, untyped strings in your form objects simply to handle invalid submissions, or to convert the Strings properly. Instead, it is often preferable to bind directly to your business objects.\r\n" + //
                                "\r\n" + //
                                "Spring's view resolution is extremely flexible. A Controller is typically responsible for preparing a model Map with data and selecting a view name but it can also write directly to the response stream and complete the request. View name resolution is highly configurable through file extension or Accept header content type negotiation, through bean names, a properties file, or even a custom ViewResolver implementation. The model (the M in MVC) is a Map interface, which allows for the complete abstraction of the view technology. You can integrate directly with template based rendering technologies such as JSP, Velocity and Freemarker, or directly generate XML, JSON, Atom, and many other types of content. The model Map is simply transformed into an appropriate format, such as JSP request attributes, a Velocity template model.\r\n" + //
                                "\r\n" + //
                                "17.1.1 Features of Spring Web MVC\r\n" + //
                                "Spring Web Flow\r\n" + //
                                "\r\n" + //
                                "Spring Web Flow (SWF) aims to be the best solution for the management of web application page flow.\r\n" + //
                                "\r\n" + //
                                "SWF integrates with existing frameworks like Spring MVC, Struts, and JSF, in both servlet and portlet environments. If you have a business process (or processes) that would benefit from a conversational model as opposed to a purely request model, then SWF may be the solution.\r\n" + //
                                "\r\n" + //
                                "SWF allows you to capture logical page flows as self-contained modules that are reusable in different situations, and as such is ideal for building web application modules that guide the user through controlled navigations that drive business processes.\r\n" + //
                                "\r\n" + //
                                "For more information about SWF, consult the Spring Web Flow website.\r\n" + //
                                "\r\n" + //
                                "Spring's web module includes many unique web support features:\r\n" + //
                                "\r\n" + //
                                "Clear separation of roles. Each role — controller, validator, command object, form object, model object, DispatcherServlet, handler mapping, view resolver, and so on — can be fulfilled by a specialized object.\r\n" + //
                                "\r\n" + //
                                "Powerful and straightforward configuration of both framework and application classes as JavaBeans. This configuration capability includes easy referencing across contexts, such as from web controllers to business objects and validators.\r\n" + //
                                "\r\n" + //
                                "Adaptability, non-intrusiveness, and flexibility. Define any controller method signature you need, possibly using one of the parameter annotations (such as @RequestParam, @RequestHeader, @PathVariable, and more) for a given scenario.\r\n" + //
                                "\r\n" + //
                                "Reusable business code, no need for duplication. Use existing business objects as command or form objects instead of mirroring them to extend a particular framework base class.\r\n" + //
                                "\r\n" + //
                                "Customizable binding and validation. Type mismatches as application-level validation errors that keep the offending value, localized date and number binding, and so on instead of String-only form objects with manual parsing and conversion to business objects.\r\n" + //
                                "\r\n" + //
                                "Customizable handler mapping and view resolution. Handler mapping and view resolution strategies range from simple URL-based configuration, to sophisticated, purpose-built resolution strategies. Spring is more flexible than web MVC frameworks that mandate a particular technique.\r\n" + //
                                "\r\n" + //
                                "Flexible model transfer. Model transfer with a name/value Map supports easy integration with any view technology.\r\n" + //
                                "\r\n" + //
                                "Customizable locale and theme resolution, support for JSPs with or without Spring tag library, support for JSTL, support for Velocity without the need for extra bridges, and so on.\r\n" + //
                                "\r\n" + //
                                "A simple yet powerful JSP tag library known as the Spring tag library that provides support for features such as data binding and themes. The custom tags allow for maximum flexibility in terms of markup code. For information on the tag library descriptor, see the appendix entitled Appendix G, spring.tld\r\n" + //
                                "\r\n" + //
                                "A JSP form tag library, introduced in Spring 2.0, that makes writing forms in JSP pages much easier. For information on the tag library descriptor, see the appendix entitled Appendix H, spring-form.tld\r\n" + //
                                "\r\n" + //
                                "Beans whose lifecycle is scoped to the current HTTP request or HTTP Session. This is not a specific feature of Spring MVC itself, but rather of the WebApplicationContext container(s) that Spring MVC uses. These bean scopes are described in Section 5.5.4, “Request, session, and global session scopes”");
            post03.setAccount(account02);
            postService.save(post03);
            Post post04 = new Post();
            post04.setTitle("Post 02");
            post04.setBody("17. Web MVC framework\r\n" + //
                                "17.1 Introduction to Spring Web MVC framework\r\n" + //
                                "The Spring Web model-view-controller (MVC) framework is designed around a DispatcherServlet that dispatches requests to handlers, with configurable handler mappings, view resolution, locale and theme resolution as well as support for uploading files. The default handler is based on the @Controller and @RequestMapping annotations, offering a wide range of flexible handling methods. With the introduction of Spring 3.0, the @Controller mechanism also allows you to create RESTful Web sites and applications, through the @PathVariable annotation and other features.\r\n" + //
                                "\r\n" + //
                                "“Open for extension...”\r\n" + //
                                "\r\n" + //
                                "A key design principle in Spring Web MVC and in Spring in general is the “Open for extension, closed for modification” principle.\r\n" + //
                                "\r\n" + //
                                "Some methods in the core classes of Spring Web MVC are marked final. As a developer you cannot override these methods to supply your own behavior. This has not been done arbitrarily, but specifically with this principle in mind.\r\n" + //
                                "\r\n" + //
                                "For an explanation of this principle, refer to Expert Spring Web MVC and Web Flow by Seth Ladd and others; specifically see the section \"A Look At Design,\" on page 117 of the first edition. Alternatively, see\r\n" + //
                                "\r\n" + //
                                "Bob Martin, The Open-Closed Principle (PDF)\r\n" + //
                                "\r\n" + //
                                "You cannot add advice to final methods when you use Spring MVC. For example, you cannot add advice to the AbstractController.setSynchronizeOnSession() method. Refer to Section 9.6.1, “Understanding AOP proxies” for more information on AOP proxies and why you cannot add advice to final methods.\r\n" + //
                                "\r\n" + //
                                "In Spring Web MVC you can use any object as a command or form-backing object; you do not need to implement a framework-specific interface or base class. Spring's data binding is highly flexible: for example, it treats type mismatches as validation errors that can be evaluated by the application, not as system errors. Thus you need not duplicate your business objects' properties as simple, untyped strings in your form objects simply to handle invalid submissions, or to convert the Strings properly. Instead, it is often preferable to bind directly to your business objects.\r\n" + //
                                "\r\n" + //
                                "Spring's view resolution is extremely flexible. A Controller is typically responsible for preparing a model Map with data and selecting a view name but it can also write directly to the response stream and complete the request. View name resolution is highly configurable through file extension or Accept header content type negotiation, through bean names, a properties file, or even a custom ViewResolver implementation. The model (the M in MVC) is a Map interface, which allows for the complete abstraction of the view technology. You can integrate directly with template based rendering technologies such as JSP, Velocity and Freemarker, or directly generate XML, JSON, Atom, and many other types of content. The model Map is simply transformed into an appropriate format, such as JSP request attributes, a Velocity template model.\r\n" + //
                                "\r\n" + //
                                "17.1.1 Features of Spring Web MVC\r\n" + //
                                "Spring Web Flow\r\n" + //
                                "\r\n" + //
                                "Spring Web Flow (SWF) aims to be the best solution for the management of web application page flow.\r\n" + //
                                "\r\n" + //
                                "SWF integrates with existing frameworks like Spring MVC, Struts, and JSF, in both servlet and portlet environments. If you have a business process (or processes) that would benefit from a conversational model as opposed to a purely request model, then SWF may be the solution.\r\n" + //
                                "\r\n" + //
                                "SWF allows you to capture logical page flows as self-contained modules that are reusable in different situations, and as such is ideal for building web application modules that guide the user through controlled navigations that drive business processes.\r\n" + //
                                "\r\n" + //
                                "For more information about SWF, consult the Spring Web Flow website.\r\n" + //
                                "\r\n" + //
                                "Spring's web module includes many unique web support features:\r\n" + //
                                "\r\n" + //
                                "Clear separation of roles. Each role — controller, validator, command object, form object, model object, DispatcherServlet, handler mapping, view resolver, and so on — can be fulfilled by a specialized object.\r\n" + //
                                "\r\n" + //
                                "Powerful and straightforward configuration of both framework and application classes as JavaBeans. This configuration capability includes easy referencing across contexts, such as from web controllers to business objects and validators.\r\n" + //
                                "\r\n" + //
                                "Adaptability, non-intrusiveness, and flexibility. Define any controller method signature you need, possibly using one of the parameter annotations (such as @RequestParam, @RequestHeader, @PathVariable, and more) for a given scenario.\r\n" + //
                                "\r\n" + //
                                "Reusable business code, no need for duplication. Use existing business objects as command or form objects instead of mirroring them to extend a particular framework base class.\r\n" + //
                                "\r\n" + //
                                "Customizable binding and validation. Type mismatches as application-level validation errors that keep the offending value, localized date and number binding, and so on instead of String-only form objects with manual parsing and conversion to business objects.\r\n" + //
                                "\r\n" + //
                                "Customizable handler mapping and view resolution. Handler mapping and view resolution strategies range from simple URL-based configuration, to sophisticated, purpose-built resolution strategies. Spring is more flexible than web MVC frameworks that mandate a particular technique.\r\n" + //
                                "\r\n" + //
                                "Flexible model transfer. Model transfer with a name/value Map supports easy integration with any view technology.\r\n" + //
                                "\r\n" + //
                                "Customizable locale and theme resolution, support for JSPs with or without Spring tag library, support for JSTL, support for Velocity without the need for extra bridges, and so on.\r\n" + //
                                "\r\n" + //
                                "A simple yet powerful JSP tag library known as the Spring tag library that provides support for features such as data binding and themes. The custom tags allow for maximum flexibility in terms of markup code. For information on the tag library descriptor, see the appendix entitled Appendix G, spring.tld\r\n" + //
                                "\r\n" + //
                                "A JSP form tag library, introduced in Spring 2.0, that makes writing forms in JSP pages much easier. For information on the tag library descriptor, see the appendix entitled Appendix H, spring-form.tld\r\n" + //
                                "\r\n" + //
                                "Beans whose lifecycle is scoped to the current HTTP request or HTTP Session. This is not a specific feature of Spring MVC itself, but rather of the WebApplicationContext container(s) that Spring MVC uses. These bean scopes are described in Section 5.5.4, “Request, session, and global session scopes”");
            post04.setAccount(account02);
            postService.save(post04);
            Post post05 = new Post();
            post05.setTitle("Post 02");
            post05.setBody("17. Web MVC framework\r\n" + //
                                "17.1 Introduction to Spring Web MVC framework\r\n" + //
                                "The Spring Web model-view-controller (MVC) framework is designed around a DispatcherServlet that dispatches requests to handlers, with configurable handler mappings, view resolution, locale and theme resolution as well as support for uploading files. The default handler is based on the @Controller and @RequestMapping annotations, offering a wide range of flexible handling methods. With the introduction of Spring 3.0, the @Controller mechanism also allows you to create RESTful Web sites and applications, through the @PathVariable annotation and other features.\r\n" + //
                                "\r\n" + //
                                "“Open for extension...”\r\n" + //
                                "\r\n" + //
                                "A key design principle in Spring Web MVC and in Spring in general is the “Open for extension, closed for modification” principle.\r\n" + //
                                "\r\n" + //
                                "Some methods in the core classes of Spring Web MVC are marked final. As a developer you cannot override these methods to supply your own behavior. This has not been done arbitrarily, but specifically with this principle in mind.\r\n" + //
                                "\r\n" + //
                                "For an explanation of this principle, refer to Expert Spring Web MVC and Web Flow by Seth Ladd and others; specifically see the section \"A Look At Design,\" on page 117 of the first edition. Alternatively, see\r\n" + //
                                "\r\n" + //
                                "Bob Martin, The Open-Closed Principle (PDF)\r\n" + //
                                "\r\n" + //
                                "You cannot add advice to final methods when you use Spring MVC. For example, you cannot add advice to the AbstractController.setSynchronizeOnSession() method. Refer to Section 9.6.1, “Understanding AOP proxies” for more information on AOP proxies and why you cannot add advice to final methods.\r\n" + //
                                "\r\n" + //
                                "In Spring Web MVC you can use any object as a command or form-backing object; you do not need to implement a framework-specific interface or base class. Spring's data binding is highly flexible: for example, it treats type mismatches as validation errors that can be evaluated by the application, not as system errors. Thus you need not duplicate your business objects' properties as simple, untyped strings in your form objects simply to handle invalid submissions, or to convert the Strings properly. Instead, it is often preferable to bind directly to your business objects.\r\n" + //
                                "\r\n" + //
                                "Spring's view resolution is extremely flexible. A Controller is typically responsible for preparing a model Map with data and selecting a view name but it can also write directly to the response stream and complete the request. View name resolution is highly configurable through file extension or Accept header content type negotiation, through bean names, a properties file, or even a custom ViewResolver implementation. The model (the M in MVC) is a Map interface, which allows for the complete abstraction of the view technology. You can integrate directly with template based rendering technologies such as JSP, Velocity and Freemarker, or directly generate XML, JSON, Atom, and many other types of content. The model Map is simply transformed into an appropriate format, such as JSP request attributes, a Velocity template model.\r\n" + //
                                "\r\n" + //
                                "17.1.1 Features of Spring Web MVC\r\n" + //
                                "Spring Web Flow\r\n" + //
                                "\r\n" + //
                                "Spring Web Flow (SWF) aims to be the best solution for the management of web application page flow.\r\n" + //
                                "\r\n" + //
                                "SWF integrates with existing frameworks like Spring MVC, Struts, and JSF, in both servlet and portlet environments. If you have a business process (or processes) that would benefit from a conversational model as opposed to a purely request model, then SWF may be the solution.\r\n" + //
                                "\r\n" + //
                                "SWF allows you to capture logical page flows as self-contained modules that are reusable in different situations, and as such is ideal for building web application modules that guide the user through controlled navigations that drive business processes.\r\n" + //
                                "\r\n" + //
                                "For more information about SWF, consult the Spring Web Flow website.\r\n" + //
                                "\r\n" + //
                                "Spring's web module includes many unique web support features:\r\n" + //
                                "\r\n" + //
                                "Clear separation of roles. Each role — controller, validator, command object, form object, model object, DispatcherServlet, handler mapping, view resolver, and so on — can be fulfilled by a specialized object.\r\n" + //
                                "\r\n" + //
                                "Powerful and straightforward configuration of both framework and application classes as JavaBeans. This configuration capability includes easy referencing across contexts, such as from web controllers to business objects and validators.\r\n" + //
                                "\r\n" + //
                                "Adaptability, non-intrusiveness, and flexibility. Define any controller method signature you need, possibly using one of the parameter annotations (such as @RequestParam, @RequestHeader, @PathVariable, and more) for a given scenario.\r\n" + //
                                "\r\n" + //
                                "Reusable business code, no need for duplication. Use existing business objects as command or form objects instead of mirroring them to extend a particular framework base class.\r\n" + //
                                "\r\n" + //
                                "Customizable binding and validation. Type mismatches as application-level validation errors that keep the offending value, localized date and number binding, and so on instead of String-only form objects with manual parsing and conversion to business objects.\r\n" + //
                                "\r\n" + //
                                "Customizable handler mapping and view resolution. Handler mapping and view resolution strategies range from simple URL-based configuration, to sophisticated, purpose-built resolution strategies. Spring is more flexible than web MVC frameworks that mandate a particular technique.\r\n" + //
                                "\r\n" + //
                                "Flexible model transfer. Model transfer with a name/value Map supports easy integration with any view technology.\r\n" + //
                                "\r\n" + //
                                "Customizable locale and theme resolution, support for JSPs with or without Spring tag library, support for JSTL, support for Velocity without the need for extra bridges, and so on.\r\n" + //
                                "\r\n" + //
                                "A simple yet powerful JSP tag library known as the Spring tag library that provides support for features such as data binding and themes. The custom tags allow for maximum flexibility in terms of markup code. For information on the tag library descriptor, see the appendix entitled Appendix G, spring.tld\r\n" + //
                                "\r\n" + //
                                "A JSP form tag library, introduced in Spring 2.0, that makes writing forms in JSP pages much easier. For information on the tag library descriptor, see the appendix entitled Appendix H, spring-form.tld\r\n" + //
                                "\r\n" + //
                                "Beans whose lifecycle is scoped to the current HTTP request or HTTP Session. This is not a specific feature of Spring MVC itself, but rather of the WebApplicationContext container(s) that Spring MVC uses. These bean scopes are described in Section 5.5.4, “Request, session, and global session scopes”");
            post05.setAccount(account02);
            postService.save(post05);
            Post post06 = new Post();
            post06.setTitle("Post 02");
            post06.setBody("17. Web MVC framework\r\n" + //
                                "17.1 Introduction to Spring Web MVC framework\r\n" + //
                                "The Spring Web model-view-controller (MVC) framework is designed around a DispatcherServlet that dispatches requests to handlers, with configurable handler mappings, view resolution, locale and theme resolution as well as support for uploading files. The default handler is based on the @Controller and @RequestMapping annotations, offering a wide range of flexible handling methods. With the introduction of Spring 3.0, the @Controller mechanism also allows you to create RESTful Web sites and applications, through the @PathVariable annotation and other features.\r\n" + //
                                "\r\n" + //
                                "“Open for extension...”\r\n" + //
                                "\r\n" + //
                                "A key design principle in Spring Web MVC and in Spring in general is the “Open for extension, closed for modification” principle.\r\n" + //
                                "\r\n" + //
                                "Some methods in the core classes of Spring Web MVC are marked final. As a developer you cannot override these methods to supply your own behavior. This has not been done arbitrarily, but specifically with this principle in mind.\r\n" + //
                                "\r\n" + //
                                "For an explanation of this principle, refer to Expert Spring Web MVC and Web Flow by Seth Ladd and others; specifically see the section \"A Look At Design,\" on page 117 of the first edition. Alternatively, see\r\n" + //
                                "\r\n" + //
                                "Bob Martin, The Open-Closed Principle (PDF)\r\n" + //
                                "\r\n" + //
                                "You cannot add advice to final methods when you use Spring MVC. For example, you cannot add advice to the AbstractController.setSynchronizeOnSession() method. Refer to Section 9.6.1, “Understanding AOP proxies” for more information on AOP proxies and why you cannot add advice to final methods.\r\n" + //
                                "\r\n" + //
                                "In Spring Web MVC you can use any object as a command or form-backing object; you do not need to implement a framework-specific interface or base class. Spring's data binding is highly flexible: for example, it treats type mismatches as validation errors that can be evaluated by the application, not as system errors. Thus you need not duplicate your business objects' properties as simple, untyped strings in your form objects simply to handle invalid submissions, or to convert the Strings properly. Instead, it is often preferable to bind directly to your business objects.\r\n" + //
                                "\r\n" + //
                                "Spring's view resolution is extremely flexible. A Controller is typically responsible for preparing a model Map with data and selecting a view name but it can also write directly to the response stream and complete the request. View name resolution is highly configurable through file extension or Accept header content type negotiation, through bean names, a properties file, or even a custom ViewResolver implementation. The model (the M in MVC) is a Map interface, which allows for the complete abstraction of the view technology. You can integrate directly with template based rendering technologies such as JSP, Velocity and Freemarker, or directly generate XML, JSON, Atom, and many other types of content. The model Map is simply transformed into an appropriate format, such as JSP request attributes, a Velocity template model.\r\n" + //
                                "\r\n" + //
                                "17.1.1 Features of Spring Web MVC\r\n" + //
                                "Spring Web Flow\r\n" + //
                                "\r\n" + //
                                "Spring Web Flow (SWF) aims to be the best solution for the management of web application page flow.\r\n" + //
                                "\r\n" + //
                                "SWF integrates with existing frameworks like Spring MVC, Struts, and JSF, in both servlet and portlet environments. If you have a business process (or processes) that would benefit from a conversational model as opposed to a purely request model, then SWF may be the solution.\r\n" + //
                                "\r\n" + //
                                "SWF allows you to capture logical page flows as self-contained modules that are reusable in different situations, and as such is ideal for building web application modules that guide the user through controlled navigations that drive business processes.\r\n" + //
                                "\r\n" + //
                                "For more information about SWF, consult the Spring Web Flow website.\r\n" + //
                                "\r\n" + //
                                "Spring's web module includes many unique web support features:\r\n" + //
                                "\r\n" + //
                                "Clear separation of roles. Each role — controller, validator, command object, form object, model object, DispatcherServlet, handler mapping, view resolver, and so on — can be fulfilled by a specialized object.\r\n" + //
                                "\r\n" + //
                                "Powerful and straightforward configuration of both framework and application classes as JavaBeans. This configuration capability includes easy referencing across contexts, such as from web controllers to business objects and validators.\r\n" + //
                                "\r\n" + //
                                "Adaptability, non-intrusiveness, and flexibility. Define any controller method signature you need, possibly using one of the parameter annotations (such as @RequestParam, @RequestHeader, @PathVariable, and more) for a given scenario.\r\n" + //
                                "\r\n" + //
                                "Reusable business code, no need for duplication. Use existing business objects as command or form objects instead of mirroring them to extend a particular framework base class.\r\n" + //
                                "\r\n" + //
                                "Customizable binding and validation. Type mismatches as application-level validation errors that keep the offending value, localized date and number binding, and so on instead of String-only form objects with manual parsing and conversion to business objects.\r\n" + //
                                "\r\n" + //
                                "Customizable handler mapping and view resolution. Handler mapping and view resolution strategies range from simple URL-based configuration, to sophisticated, purpose-built resolution strategies. Spring is more flexible than web MVC frameworks that mandate a particular technique.\r\n" + //
                                "\r\n" + //
                                "Flexible model transfer. Model transfer with a name/value Map supports easy integration with any view technology.\r\n" + //
                                "\r\n" + //
                                "Customizable locale and theme resolution, support for JSPs with or without Spring tag library, support for JSTL, support for Velocity without the need for extra bridges, and so on.\r\n" + //
                                "\r\n" + //
                                "A simple yet powerful JSP tag library known as the Spring tag library that provides support for features such as data binding and themes. The custom tags allow for maximum flexibility in terms of markup code. For information on the tag library descriptor, see the appendix entitled Appendix G, spring.tld\r\n" + //
                                "\r\n" + //
                                "A JSP form tag library, introduced in Spring 2.0, that makes writing forms in JSP pages much easier. For information on the tag library descriptor, see the appendix entitled Appendix H, spring-form.tld\r\n" + //
                                "\r\n" + //
                                "Beans whose lifecycle is scoped to the current HTTP request or HTTP Session. This is not a specific feature of Spring MVC itself, but rather of the WebApplicationContext container(s) that Spring MVC uses. These bean scopes are described in Section 5.5.4, “Request, session, and global session scopes”");
            post06.setAccount(account02);
            postService.save(post06);
        }
    }
    
}
