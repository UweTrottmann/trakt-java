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

package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.entities.AddNoteRequest;
import com.uwetrottmann.trakt5.entities.Note;
import com.uwetrottmann.trakt5.entities.UpdateNoteRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Notes {

    /**
     * <b>VIP Only, OAuth Required</b>
     * <p>
     * Add a note (maximum 500 characters). Note: this also replaces an existing note.
     * <p>
     * <a href="https://trakt.docs.apiary.io/#reference/notes/notes/add-notes">Online documentation</a>
     */
    @POST("notes")
    Call<Note> addNote(
            @Body AddNoteRequest request
    );

    /**
     * <b>VIP Only, OAuth Required</b>
     * <p>
     * Get a note.
     * <p>
     * <a href="https://trakt.docs.apiary.io/#reference/notes/note/get-a-note">Online documentation</a>
     */
    @GET("notes/{id}")
    Call<Note> getNote(
            @Path("id") long id
    );

    /**
     * <b>VIP Only, OAuth Required</b>
     * <p>
     * Update a note (maximum 500 characters).
     * <p>
     * <a href="https://trakt.docs.apiary.io/#reference/notes/note/update-a-note">Online documentation</a>
     */
    @PUT("notes/{id}")
    Call<Note> updateNote(
            @Path("id") long id,
            @Body UpdateNoteRequest request
    );

    /**
     * <b>VIP Only, OAuth Required</b>
     * <p>
     * Delete a note.
     * <p>
     * <a href="https://trakt.docs.apiary.io/#reference/notes/note/delete-a-note">Online documentation</a>
     */
    @DELETE("notes/{id}")
    Call<Void> deleteNote(
            @Path("id") long id
    );
}
