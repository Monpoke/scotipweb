/*
 * Copyright (c) 2016. Pierre BOURGEOIS
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software, and
 *  to permit persons to whom the Software is furnished to do so, subject
 *  to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package scotip.app.service.operator;

import scotip.app.dto.QueueDto;
import scotip.app.dto.QueueOperatorDto;
import scotip.app.model.MohGroup;
import scotip.app.model.Queue;
import scotip.app.model.Switchboard;

import java.io.IOException;
import java.util.List;

/**
 * Created by svevia on 18/05/2016.
 */
public interface QueueService {
    List<Queue> getQueuesFromSwitchboard(Switchboard switchboard);

    void registerNewQueue(Switchboard switchboard, QueueDto queueDto) throws IOException;

    void removeQueue(Queue queue);

    Queue getQueueWithSwitchboardAndId(Switchboard switchboard, int qid);

    void saveOperators(Queue queue, QueueOperatorDto queueOperatorDto);

    Queue getQueueFromId(Integer id);

    void notifyServerReload(Switchboard switchboard);
}
