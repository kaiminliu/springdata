package cn.liuminkai.nameonly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;


@Component
@Value
public class CustomerNameOnly {
    private String custName;
}
