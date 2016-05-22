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

import com.github.slugify.Slugify;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.config.MainConfig;
import scotip.app.dao.operator.OperatorDao;
import scotip.app.dao.operator.QueueDao;
import scotip.app.dto.QueueDto;
import scotip.app.dto.QueueOperatorDto;
import scotip.app.model.MohGroup;
import scotip.app.model.Queue;
import scotip.app.model.Switchboard;
import scotip.app.service.company.CompanyService;

import java.io.IOException;
import java.util.List;

/**
 * Created by svevia on 18/05/2016.
 */
@Service("QueueService")
@Transactional
public class QueueServiceImpl implements QueueService {

    @Autowired
    private OperatorDao dao;

    @Autowired
    private CompanyService company;

    @Autowired
    private QueueDao queueDao;

    @Override
    public List<Queue> getQueuesFromSwitchboard(Switchboard switchboard) {
        return queueDao.getQueuesFromSwitchboard(switchboard);
    }

    @Override
    public void registerNewQueue(Switchboard switchboard, QueueDto queueDto) throws IOException {
        Queue queue = new Queue();
        queue.setName(queueDto.getQueueName());
        queue.setSwitchboard(switchboard);
        queue.setAsteriskName("QUEUE_S"+switchboard.getSid()+"_"+ (new Slugify().slugify(queueDto.getQueueName())));

        queueDao.saveQueue(queue);

        notifyServerReload(switchboard);
    }

    public void notifyServerReload(Switchboard switchboard) {


        Unirest.get(MainConfig.NODESPAS_URL + "/queues/" + switchboard.getSid()).asJsonAsync(new Callback<JsonNode>() {
            @Override
            public void completed(HttpResponse<JsonNode> httpResponse) {
            }

            @Override
            public void failed(UnirestException e) {
                e.printStackTrace();
            }

            @Override
            public void cancelled() {
            }
        });
    }

    @Override
    public void removeQueue(Queue queue) {
        queueDao.removeQueue(queue);
        notifyServerReload(queue.getSwitchboard());
    }

    @Override
    public Queue getQueueWithSwitchboardAndId(Switchboard switchboard, int qid) {
        return queueDao.getQueueWithSwitchboardAndId(switchboard,qid);
    }

    @Override
    public void saveOperators(Queue queue, QueueOperatorDto queueOperatorDto) {
        queue.setOperators(queueOperatorDto.getOperators());
        queueDao.updateQueue(queue);

        notifyServerReload(queue.getSwitchboard());
    }

    @Override
    public Queue getQueueFromId(Integer id) {
        return queueDao.findById(id);
    }


}
