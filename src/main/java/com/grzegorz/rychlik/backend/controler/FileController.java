package com.grzegorz.rychlik.backend.controler;

import com.grzegorz.rychlik.backend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    public final FileService fileService;

    @GetMapping(value = "/contest/{contestId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadContestFile(@PathVariable Long contestId){
        byte[] bytes = fileService.generateCompetitionResults(contestId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        httpHeaders.add(HttpHeaders.CONTENT_LENGTH, Integer.toString(bytes.length));
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=wyniki.pdf");
        return ResponseEntity.ok().headers(httpHeaders).body(bytes);

    }
}
