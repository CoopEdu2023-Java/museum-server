package cn.moonshotacademy.museum.dto;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestDto {   
    private String title;             
    private String intro;            
    private String competency;        
    private String category;         
    private String type;             
    private String avatar_url;
    private Set<Integer> userIds;     
    private Set<Integer> fileIds;    
}
