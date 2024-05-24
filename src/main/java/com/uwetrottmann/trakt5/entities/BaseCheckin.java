/*
 * Copyright 2024 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5.entities;

public abstract class BaseCheckin {

    /** Control sharing to any connected social networks. */
    public ShareSettings sharing;
    /** Message used for sharing. If not sent, it will use the watching string in the user settings. */
    public String message;
    /** Foursquare venue ID. */
    public String venue_id;
    /** Foursquare venue name. */
    public String venue_name;
    public String app_version;
    /** Build date of the app. */
    public String app_date;

}
