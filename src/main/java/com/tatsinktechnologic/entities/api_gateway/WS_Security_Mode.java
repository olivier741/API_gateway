/*
 * Copyright 2019 olivier.tatsinkou.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tatsinktechnologic.entities.api_gateway;

/**
 *
 * @author olivier.tatsinkou
 */
public enum WS_Security_Mode {
    NONE,SOAP_BASIC_AUTH,SOAP_HEAD_TOKEN,SOAP_HEAD_AUTH,REST_BASIC_AUTH,REST_TOKEN_AUTH,REST_TOKEN_AUTH_ROLE,REST_OAUTH1,REST_OAUTH2
}
