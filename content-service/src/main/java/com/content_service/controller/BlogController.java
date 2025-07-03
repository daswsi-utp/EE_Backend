package com.content_service.controller;

import com.content_service.dto.BlogDto;
import com.content_service.dto.BlogCreateDto;
import com.content_service.service.BlogService;
import com.content_service.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/blogs")
@CrossOrigin(origins = "*")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ImageUtil imageUtil;

    // Obtener todos los blogs publicados (público)
    @GetMapping("/public")
    public ResponseEntity<Page<BlogDto>> getPublishedBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Page<BlogDto> blogs = blogService.getAllPublishedBlogs(page, size, sortBy, sortDir);
        return ResponseEntity.ok(blogs);
    }

    // Obtener blog por ID (público)
    @GetMapping("/public/{id}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable Long id) {
        Optional<BlogDto> blog = blogService.getBlogById(id);
        return blog.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar blogs con filtros (público)
    @GetMapping("/public/search")
    public ResponseEntity<Page<BlogDto>> searchBlogs(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Page<BlogDto> blogs = blogService.searchBlogs(category, author, true, keyword, page, size, sortBy, sortDir);
        return ResponseEntity.ok(blogs);
    }

    // Obtener blogs por categoría (público)
    @GetMapping("/public/category/{category}")
    public ResponseEntity<Page<BlogDto>> getBlogsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BlogDto> blogs = blogService.getBlogsByCategory(category, page, size);
        return ResponseEntity.ok(blogs);
    }

    // Obtener blogs por autor (público)
    @GetMapping("/public/author/{author}")
    public ResponseEntity<Page<BlogDto>> getBlogsByAuthor(
            @PathVariable String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BlogDto> blogs = blogService.getBlogsByAuthor(author, page, size);
        return ResponseEntity.ok(blogs);
    }

    // Buscar por palabra clave (público)
    @GetMapping("/public/keyword")
    public ResponseEntity<Page<BlogDto>> searchByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BlogDto> blogs = blogService.searchByKeyword(keyword, page, size);
        return ResponseEntity.ok(blogs);
    }

    // Obtener categorías disponibles (público)
    @GetMapping("/public/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = blogService.getCategories();
        return ResponseEntity.ok(categories);
    }

    // Obtener autores disponibles (público)
    @GetMapping("/public/authors")
    public ResponseEntity<List<String>> getAuthors() {
        List<String> authors = blogService.getAuthors();
        return ResponseEntity.ok(authors);
    }

    // Buscar por rango de fechas (público)
    @GetMapping("/public/date-range")
    public ResponseEntity<Page<BlogDto>> getBlogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BlogDto> blogs = blogService.getBlogsByDateRange(startDate, endDate, page, size);
        return ResponseEntity.ok(blogs);
    }

    // === ENDPOINTS DE ADMINISTRACIÓN ===

    // Obtener todos los blogs (admin)
    @GetMapping("/admin")
    public ResponseEntity<Page<BlogDto>> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Page<BlogDto> blogs = blogService.getAllBlogs(page, size, sortBy, sortDir);
        return ResponseEntity.ok(blogs);
    }

    // Buscar blogs con todos los filtros (admin)
    @GetMapping("/admin/search")
    public ResponseEntity<Page<BlogDto>> adminSearchBlogs(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Boolean published,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Page<BlogDto> blogs = blogService.searchBlogs(category, author, published, keyword, page, size, sortBy, sortDir);
        return ResponseEntity.ok(blogs);
    }

    // Crear nuevo blog (admin) - NUEVO: Con soporte para imagen
    @PostMapping("/admin")
    public ResponseEntity<?> createBlog(@RequestBody BlogCreateDto createDto) {
        try {
            BlogDto createdBlog = blogService.createBlog(createDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBlog);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el blog: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // NUEVO: Crear blog con imagen
    @PostMapping("/admin/with-image")
    public ResponseEntity<?> createBlogWithImage(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("summary") String summary,
            @RequestParam("author") String author,
            @RequestParam("category") String category,
            @RequestParam(value = "published", defaultValue = "false") Boolean published,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        try {
            BlogCreateDto createDto = new BlogCreateDto();
            createDto.setTitle(title);
            createDto.setContent(content);
            createDto.setSummary(summary);
            createDto.setAuthor(author);
            createDto.setCategory(category);
            createDto.setPublished(published);

            // Si se proporciona una imagen, guardarla
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = imageUtil.saveImage(imageFile);
                String imageUrl = imageUtil.getImageUrl(fileName);
                createDto.setImageUrl(imageUrl);
            }

            BlogDto createdBlog = blogService.createBlog(createDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBlog);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear el blog: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Actualizar blog (admin)
    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Long id, @RequestBody BlogCreateDto updateDto) {
        try {
            Optional<BlogDto> updatedBlog = blogService.updateBlog(id, updateDto);
            return updatedBlog.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar el blog: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // NUEVO: Actualizar blog con imagen
    @PutMapping("/admin/{id}/with-image")
    public ResponseEntity<?> updateBlogWithImage(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("summary") String summary,
            @RequestParam("author") String author,
            @RequestParam("category") String category,
            @RequestParam(value = "published", defaultValue = "false") Boolean published,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        try {
            // Primero obtener el blog actual para manejar la imagen anterior
            Optional<BlogDto> currentBlogOpt = blogService.getBlogById(id);
            if (!currentBlogOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            BlogDto currentBlog = currentBlogOpt.get();
            String oldImageUrl = currentBlog.getImageUrl();

            BlogCreateDto updateDto = new BlogCreateDto();
            updateDto.setTitle(title);
            updateDto.setContent(content);
            updateDto.setSummary(summary);
            updateDto.setAuthor(author);
            updateDto.setCategory(category);
            updateDto.setPublished(published);

            // Si se proporciona una nueva imagen
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = imageUtil.saveImage(imageFile);
                String imageUrl = imageUtil.getImageUrl(fileName);
                updateDto.setImageUrl(imageUrl);

                // Eliminar imagen anterior si existe
                if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                    String oldFileName = oldImageUrl.substring(oldImageUrl.lastIndexOf("/") + 1);
                    imageUtil.deleteImage(oldFileName);
                }
            } else {
                // Mantener la imagen actual
                updateDto.setImageUrl(oldImageUrl);
            }

            Optional<BlogDto> updatedBlog = blogService.updateBlog(id, updateDto);
            return updatedBlog.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al actualizar el blog: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Eliminar blog (admin) - MODIFICADO: Con eliminación de imagen
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id) {
        try {
            // Primero obtener el blog para eliminar su imagen si existe
            Optional<BlogDto> blogOpt = blogService.getBlogById(id);
            if (blogOpt.isPresent()) {
                BlogDto blog = blogOpt.get();
                String imageUrl = blog.getImageUrl();

                // Eliminar el blog
                boolean deleted = blogService.deleteBlog(id);
                if (deleted) {
                    // Eliminar imagen si existe
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                        imageUtil.deleteImage(fileName);
                    }
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al eliminar el blog: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // Obtener blog por ID (admin - incluye no publicados)
    @GetMapping("/admin/{id}")
    public ResponseEntity<BlogDto> getAdminBlogById(@PathVariable Long id) {
        Optional<BlogDto> blog = blogService.getBlogById(id);
        return blog.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}