package backend_blogapp.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;      // List of items for the current page
    private int pageNumber;       // Current page number (zero-based)
    private int pageSize;         // Size of the page
    private long totalElements;   // Total number of items across all pages
    private int totalPages;       // Total pages available
    private boolean last;         // Is this the last page?
}
