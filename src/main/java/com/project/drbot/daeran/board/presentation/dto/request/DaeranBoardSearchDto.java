package com.project.drbot.daeran.board.presentation.dto.request;

import com.project.drbot.daeran.board.domain.FilterOption;
import lombok.*;
import org.thymeleaf.util.StringUtils;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DaeranBoardSearchDto {

    private Integer page;
    private Integer size;
    private String title;
    private String siteName;
    private String category;
    private String section;
    private String filterOption;

    public FilterOption getFilterOption(){
        if(!StringUtils.isEmpty(siteName) && !StringUtils.isEmpty(category)){
            return FilterOption.ALL;
        }else if(StringUtils.isEmpty(siteName) && !StringUtils.isEmpty(category)){
            return FilterOption.CATEGORY;
        }else if(!StringUtils.isEmpty(siteName) && StringUtils.isEmpty(category)){
            return FilterOption.SITE_NAME;
        }else {
            return FilterOption.NO_FILTER;
        }
    }
}
