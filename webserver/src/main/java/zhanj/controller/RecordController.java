package zhanj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import zhanj.entity.Record;
import zhanj.entity.Result;
import zhanj.repo.RecordRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/record")
public class RecordController {
    private RecordRepository recRepo;
    private Logger log = LoggerFactory.getLogger(RecordController.class);
    private Page<Record> records = null;

    public RecordController(@Autowired RecordRepository recRepo) {
        this.recRepo = recRepo;
    }

    @PostMapping("/add")
    public Result addRecord(
            @RequestParam String objectId,
            @RequestParam String title,
            @RequestParam String name,
            @RequestParam String secret,
            @RequestParam String note
    ) {
        Record record = new Record();
        record.setObjectId(objectId);
        record.setTitle(title);
        record.setName(name);
        record.setSecret(secret);
        record.setNote(note);
        record.setUpdatedAt(new Date());
        record.setCreatedAt(record.getUpdatedAt());
        record = recRepo.save(record);
        return new Result(0, "saved record id: " + record.getId());
    }

    @GetMapping("/list")
    public Result getRecords(
            @RequestParam(required = false, defaultValue = "0") int pageIndex,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        if (records == null) {
            log.info("populate cache 'records'");
            records = recRepo.findAll(PageRequest.of(pageIndex, pageSize, new Sort(Sort.Direction.ASC, "updatedAt")));
        } else {
            log.info("use cache directly.");
        }
        Map<String, Object> data = new HashMap<>(3);
        data.put("records", records.getContent());
        data.put("totalRecords", records.getTotalElements());
        data.put("totalPages", records.getTotalPages());
        return new Result(0, null, data);
    }
}
