<!--
# license: Licensed to the Apache Software Foundation (ASF) under one
#         or more contributor license agreements.  See the NOTICE file
#         distributed with this work for additional information
#         regarding copyright ownership.  The ASF licenses this file
#         to you under the Apache License, Version 2.0 (the
#         "License"); you may not use this file except in compliance
#         with the License.  You may obtain a copy of the License at
#
#           http://www.apache.org/licenses/LICENSE-2.0
#
#         Unless required by applicable law or agreed to in writing,
#         software distributed under the License is distributed on an
#         "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
#         KIND, either express or implied.  See the License for the
#         specific language governing permissions and limitations
#         under the License.
-->

# cordova-plugin-hint-request

This plugin provides functionality of Google Services Api - Mobile Number hint request for Android And return the Selected Mobile Number from Hint Request Popup

## Installation

    cordova plugin add cordova-plugin-hint-request

## Methods

- `showHintRequest`

### Example

    var success = function (result) {
        alert(JSON.stringify(result));
    }
    var failure = function (result) {
        alert(JSON.stringify(result);
    }
    cordova.plugins.CordovaHintRequest.showHintRequest({}, success, failure);

### Supported Platforms

- Android

### Android Quirks

-

-
