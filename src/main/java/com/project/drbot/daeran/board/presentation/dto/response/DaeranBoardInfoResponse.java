package com.project.drbot.daeran.board.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.drbot.daeran.board.domain.DaeranBoardEntity;
import com.project.drbot.daeran.reply.presentation.dto.response.DaeranReplyInfoResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DaeranBoardInfoResponse {

    @JsonIgnore
    private DaeranBoardEntity entity;

    public DaeranBoardInfoResponse(DaeranBoardEntity entity) {
        this.entity = entity;
    }

    public Long getId() {
        return entity.getId();
    }

    public String getTitle() {
        return entity.getTitle();
    }

    public String getPrice() {
        return entity.getPrice();
    }

    public String getCategory() {
        return entity.getCategory();
    }

    public String getCategoryNm() {
        String categoryNm = "";
        if (entity.getCategory() != null) {
            switch (entity.getCategory()) {
                case "1":
                    categoryNm = "전자/가전";
                    break;
                case "2":
                    categoryNm = "의류/잡화";
                    break;
                case "3":
                    categoryNm = "식품/건강";
                    break;
                case "4":
                    categoryNm = "쿠폰/상품권";
                    break;
                default:
                    categoryNm = "기타";
                    break;
            }
        }
        return categoryNm;
    }

    public String getDescription() {
        return entity.getDescription();
    }

    public String getLink() {
        return entity.getLink();
    }

    public LocalDateTime getRegDate() {
        return entity.getRegDate();
    }

    public List<DaeranReplyInfoResponse> getReplyList() {
        return entity.getDaeranReplyList().stream().map(DaeranReplyInfoResponse::new).collect(Collectors.toList());
    }

    public String getSection() {
        return entity.getSection();
    }

    public String getSiteName() {
        return entity.getSiteName();
    }

    public String getLogoPath() {
        return entity.getLogoPath();
    }

    public Long getViewCount() {
        return entity.getViewCount();
    }

    public Long getLikeCount() {
        return entity.getLikeCount();
    }



    public static int solution(int[] citations) {
        // n편중 h번이상 인용된 논문이 h편이상이고 나머지 논문이 h번 이하 인용되었다면 h의 최대값이 h-index
        int idx = 0;
        int H_idx = 0;
        int len = citations.length;

        Arrays.sort(citations);
        while (true){
            if((len - idx) > H_idx){
                if(H_idx < citations[idx]){
                    H_idx ++;
                } else {
                    idx ++;
                }
            } else{
                break;
            }
        }

        return H_idx;
    }

    public static void main(String[] args){
        int[] arr = {3, 0, 6, 1, 5};
        System.out.println(solution(arr));
    }

}
