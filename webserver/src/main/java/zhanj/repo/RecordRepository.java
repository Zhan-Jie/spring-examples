package zhanj.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import zhanj.entity.Record;

public interface RecordRepository extends PagingAndSortingRepository<Record, Integer>{

}
