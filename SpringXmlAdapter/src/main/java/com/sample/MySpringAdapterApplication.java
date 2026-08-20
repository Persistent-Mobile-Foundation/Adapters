/**
 *    © Copyright 2016 IBM Corp.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.sample;

import com.github.mfpdev.adapters.spring.integration.SpringAnnotationApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * JAX-RS application entry point for the Spring-annotation-based adapter.
 *
 * <p>Registered in {@code adapter.xml} via the {@code JAXRSApplicationClass}
 * element.  The {@link ComponentScan} on the {@code com.sample} package
 * causes Spring to auto-discover {@link MySpringXmlAdapterResource} (annotated
 * with {@code @Component}) and {@link com.sample.impl.HelloServiceImpl}
 * (annotated with {@code @Service}).
 */
@Configuration
@ComponentScan("com.sample")
public class MySpringAdapterApplication extends SpringAnnotationApplication {
}
