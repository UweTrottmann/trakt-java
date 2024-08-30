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

import com.uwetrottmann.trakt5.BaseTestCase;
import com.uwetrottmann.trakt5.TestData;
import com.uwetrottmann.trakt5.entities.AddNoteRequest;
import com.uwetrottmann.trakt5.entities.Note;
import com.uwetrottmann.trakt5.entities.Show;
import com.uwetrottmann.trakt5.entities.ShowIds;
import com.uwetrottmann.trakt5.entities.UpdateNoteRequest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NotesTest extends BaseTestCase {

    @Test
    public void note_addGetUpdateDelete() throws Exception {
        Notes notes = getTrakt().notes();

        // Add note
        // Note: an existing note is overwritten, so no need to delete before this test
        Show show = new Show();
        show.ids = ShowIds.trakt(TestData.SHOW_TRAKT_ID);
        Note note = executeCall(notes.addNote(new AddNoteRequest(show, "A very good show! ❤")));
        assertThat(note.id).isPositive();
        // Note: Trakt replaces emoji with shortcodes :(
        // https://trakt.docs.apiary.io/#introduction/emojis
        assertThat(note.notes).isEqualTo("A very good show! :heart:");

        Thread.sleep(500); // Give server time to update state

        // Get note
        Note readNote = executeCall(notes.getNote(note.id));
        assertThat(readNote.id).isEqualTo(note.id);

        // Update note
        String updatedText = "This note was updated!";
        Note updatedNote = executeCall(notes.updateNote(readNote.id, new UpdateNoteRequest(updatedText)));
        assertThat(updatedNote.id).isEqualTo(readNote.id);
        assertThat(updatedNote.notes).isEqualTo(updatedText);

        Thread.sleep(500); // Give server time to update state

        // Delete note
        executeVoidCall(notes.deleteNote(updatedNote.id));
    }

}
