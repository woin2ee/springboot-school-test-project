package iducs.springboot.yjwboard.domain;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    private List<DTO> dtoList;
    private int totalPage;

    private int currentPage;
    private int sizeOfPage;

    private int startPage, endPage;
    private boolean isPrevPage, isNextPage;

    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    public void makePageList(Pageable pageable) {
        this.currentPage = pageable.getPageNumber() + 1;
        this.sizeOfPage = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(currentPage/(double) sizeOfPage)) * sizeOfPage;

        startPage = tempEnd - (sizeOfPage - 1);
        endPage = Math.min(totalPage, tempEnd);
        isPrevPage = startPage > 1;
        isNextPage = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
    }
}
