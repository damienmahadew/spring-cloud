package com.damien.bank.information.config;

import brave.Tracing;
import brave.context.log4j2.ThreadContextCurrentTraceContext;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

@Configuration
public class TracingConfig {

//    @Bean
//    Tracing tracing(@Value("${spring.application.name}") String serviceName,
//                    Reporter<Span> spanReporter) {
//        return Tracing
//                .newBuilder()
//                .sampler(Sampler.ALWAYS_SAMPLE)
//                .localServiceName(serviceName)
//                .propagationFactory(ExtraFieldPropagation
//                        .newFactory(B3Propagation.FACTORY, "client-id"))
//                .currentTraceContext(ThreadContextCurrentTraceContext.create())
//                .spanReporter(spanReporter)
//                .build();
//    }
//
//    @Bean
//    HttpTracing httpTracing(Tracing tracing) {
//        return HttpTracing.create(tracing);
//    }

}
