package com.example.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BasicErrorControllerClass {




    /**
     * Basic global error {@link Controller @Controller}, rendering {@link ErrorAttributes}.
     * More specific errors can be handled either using Spring MVC abstractions (e.g.
     * {@code @ExceptionHandler}) or by adding servlet
     * {@link AbstractServletWebServerFactory#setErrorPages server error pages}.
     *
     * @author Dave Syer
     * @author Phillip Webb
     * @author Michael Stummvoll
     * @author Stephane Nicoll
     * @author Scott Frederick
     * @since 1.0.0
     * @see ErrorAttributes
     * @see ErrorProperties
     */
    @Controller
    @RequestMapping("${server.error.path:${error.path:/error}}")
    public class BasicErrorController extends AbstractErrorController {

        private final ErrorProperties errorProperties;

        /**
         * Create a new {@link BasicErrorController} instance.
         * @param errorAttributes the error attributes
         * @param errorProperties configuration properties
         */
        public BasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
            this(errorAttributes, errorProperties, Collections.emptyList());
        }

        /**
         * Create a new {@link BasicErrorController} instance.
         * @param errorAttributes the error attributes
         * @param errorProperties configuration properties
         * @param errorViewResolvers error view resolvers
         */
        public BasicErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties,
                                    List<ErrorViewResolver> errorViewResolvers) {
            super(errorAttributes, errorViewResolvers);
            Assert.notNull(errorProperties, "ErrorProperties must not be null");
            this.errorProperties = errorProperties;
        }

        @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
        public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
            HttpStatus status = getStatus(request);
            Map<String, Object> model = Collections
                    .unmodifiableMap(getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
            response.setStatus(status.value());
            ModelAndView modelAndView = resolveErrorView(request, response, status, model);
            return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
        }

        @RequestMapping
        public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
            HttpStatus status = getStatus(request);
            if (status == HttpStatus.NO_CONTENT) {
                return new ResponseEntity<>(status);
            }
            Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
            return new ResponseEntity<>(body, status);
        }

        @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
        public ResponseEntity<String> mediaTypeNotAcceptable(HttpServletRequest request) {
            HttpStatus status = getStatus(request);
            return ResponseEntity.status(status).build();
        }

        protected ErrorAttributeOptions getErrorAttributeOptions(HttpServletRequest request, MediaType mediaType) {
            ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
            if (this.errorProperties.isIncludeException()) {
                options = options.including(ErrorAttributeOptions.Include.EXCEPTION);
            }
            if (isIncludeStackTrace(request, mediaType)) {
                options = options.including(ErrorAttributeOptions.Include.STACK_TRACE);
            }
            if (isIncludeMessage(request, mediaType)) {
                options = options.including(ErrorAttributeOptions.Include.MESSAGE);
            }
            if (isIncludeBindingErrors(request, mediaType)) {
                options = options.including(ErrorAttributeOptions.Include.BINDING_ERRORS);
            }
            return options;
        }

        /**
         * Determine if the stacktrace attribute should be included.
         * @param request the source request
         * @param produces the media type produced (or {@code MediaType.ALL})
         * @return if the stacktrace attribute should be included
         */
        protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
            switch (getErrorProperties().getIncludeStacktrace()) {
                case ALWAYS:
                    return true;
                case ON_PARAM:
                    return getTraceParameter(request);
                default:
                    return false;
            }
        }

        /**
         * Determine if the message attribute should be included.
         * @param request the source request
         * @param produces the media type produced (or {@code MediaType.ALL})
         * @return if the message attribute should be included
         */
        protected boolean isIncludeMessage(HttpServletRequest request, MediaType produces) {
            switch (getErrorProperties().getIncludeMessage()) {
                case ALWAYS:
                    return true;
                case ON_PARAM:
                    return getMessageParameter(request);
                default:
                    return false;
            }
        }

        /**
         * Determine if the errors attribute should be included.
         * @param request the source request
         * @param produces the media type produced (or {@code MediaType.ALL})
         * @return if the errors attribute should be included
         */
        protected boolean isIncludeBindingErrors(HttpServletRequest request, MediaType produces) {
            switch (getErrorProperties().getIncludeBindingErrors()) {
                case ALWAYS:
                    return true;
                case ON_PARAM:
                    return getErrorsParameter(request);
                default:
                    return false;
            }
        }

        /**
         * Provide access to the error properties.
         * @return the error properties
         */
        protected ErrorProperties getErrorProperties() {
            return this.errorProperties;
        }

    }

    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}
