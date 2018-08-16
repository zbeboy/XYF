package com.rongxingyn.xyf.config

import com.rongxingyn.xyf.security.AjaxAuthenticationFailureHandler
import com.rongxingyn.xyf.security.AjaxAuthenticationSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import com.rongxingyn.xyf.filter.SecurityLoginFilter
import javax.inject.Inject
import javax.sql.DataSource


/**
 * spring security 配置.
 *
 * @author zbeboy
 * @version 1.1
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
open class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Qualifier("dataSource")
    @Autowired
    open lateinit var dataSource: DataSource

    @Inject
    open lateinit var ajaxAuthenticationSuccessHandler: AjaxAuthenticationSuccessHandler

    @Inject
    open lateinit var ajaxAuthenticationFailureHandler: AjaxAuthenticationFailureHandler

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun jdbcTokenRepository(): JdbcTokenRepositoryImpl {
        val j = JdbcTokenRepositoryImpl()
        j.dataSource = this.dataSource
        return j
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .headers()
                // allow same origin to frame our site to support iframe SockJS
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/images/**").permitAll()
                .and().formLogin().loginPage("/login")
                .successHandler(this.ajaxAuthenticationSuccessHandler)
                .failureHandler(this.ajaxAuthenticationFailureHandler)
                .and().sessionManagement().invalidSessionUrl("/login")
                .and().logout().logoutSuccessUrl("/")
                .permitAll().invalidateHttpSession(true)
                .and().rememberMe().tokenValiditySeconds(2419200).rememberMeParameter("remember-me").tokenRepository(jdbcTokenRepository())
                .and().authorizeRequests().antMatchers("/web/backstage/article/**").hasAnyRole("ADMIN", "MAINTAIN")
                .and().authorizeRequests().antMatchers("/web/backstage/en/article/**").hasAnyRole("ADMIN", "MAINTAIN")
                .and().authorizeRequests().antMatchers("/web/backstage/banner/**").hasAnyRole("ADMIN")
                .and().authorizeRequests().antMatchers("/web/backstage/files/**").hasAnyRole("ADMIN")
                .and().authorizeRequests().antMatchers("/web/backstage/links/**").hasAnyRole("ADMIN")
                .and().authorizeRequests().antMatchers("/web/backstage/menus/**").hasAnyRole("ADMIN")
                .and().authorizeRequests().antMatchers("/web/backstage/users/**").hasAnyRole("ADMIN", "MAINTAIN")
                .and().authorizeRequests().antMatchers("/web/backstage/website/**").hasAnyRole("ADMIN")
                .and().authorizeRequests().antMatchers("/web/**").hasAnyRole("ADMIN", "MAINTAIN")
                .and().authorizeRequests().antMatchers("/user/**", "/data/**").permitAll()
                .antMatchers("/metrics/**").hasRole("ACTUATOR")
                .antMatchers("/health/**").hasRole("ACTUATOR")
                .antMatchers("/trace/**").hasRole("ACTUATOR")
                .antMatchers("/dump/**").hasRole("ACTUATOR")
                .antMatchers("/shutdown/**").hasRole("ACTUATOR")
                .antMatchers("/beans/**").hasRole("ACTUATOR")
                .antMatchers("/configprops/**").hasRole("ACTUATOR")
                .antMatchers("/info/**").hasRole("ACTUATOR")
                .antMatchers("/autoconfig/**").hasRole("ACTUATOR")
                .antMatchers("/env/**").hasRole("ACTUATOR")
                .antMatchers("/mappings/**").hasRole("ACTUATOR")
                .and().addFilterBefore(SecurityLoginFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder()).and().eraseCredentials(false)
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}