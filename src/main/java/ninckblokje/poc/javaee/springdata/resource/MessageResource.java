/*
 * Copyright (c) 2021, ninckblokje
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ninckblokje.poc.javaee.springdata.resource;

import ninckblokje.poc.javaee.springdata.entity.Message;
import ninckblokje.poc.javaee.springdata.repository.MessageRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import java.util.List;

@Path("/message")
@ApplicationScoped
public class MessageResource {

    @Inject
    private MessageRepository messageRepository;

    @GET
    @Produces("application/json")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @GET
    @Path("/count")
    @Produces("text/plain")
    public long countAllMessages() {
        return messageRepository.count();
    }

    @POST
    @Transactional
    public void newMessage(@QueryParam("text") @NotBlank @Valid String text) {
        var msg = new Message();
        msg.setText(text);

        messageRepository.save(msg);

        if ("crash".equals(text)) {
            throw new RuntimeException("Crashing!");
        }
    }
}
