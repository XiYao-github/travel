package travel.dto;

import lombok.Data;

@Data
public class NotesDetailedDto {
    /**
     * 段落id
     */
    private Integer id;
    /**
     * 段落标题
     */
    private String title;

    /**
     * 段落内容
     */
    private String content;
}
