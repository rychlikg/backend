package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.model.dao.Contest;
import com.grzegorz.rychlik.backend.model.dao.Participant;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final ParticipantService participantService;
    private final ContestService contestService;

    public byte[] generateCompetitionResults(Long contestId){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document(new PdfDocument(new PdfWriter(byteArrayOutputStream)));
        Contest contest = contestService.getById(contestId);
        Paragraph paragraph = new Paragraph();
        paragraph.add(contest.getCompetition().getName());
        document.add(paragraph);
        Paragraph paragraph2 = new Paragraph();
        paragraph2.add(contest.getName());
        document.add(paragraph2);
        Table table = new Table(5);
        table.setWidth(new UnitValue(2,100));
        table.addHeaderCell("Miejsce");
        table.addHeaderCell("Zawodnik");
        table.addHeaderCell("Kon");
        table.addHeaderCell("Czas");
        table.addHeaderCell("Punkty");
        List<Participant> participantsByContestId = participantService.getParticipantsByContestId(contestId);
        Integer place = 1;
        for (Participant participant : participantsByContestId) {
            table.addCell(place.toString());
            table.addCell(participant.getUser().getLastName()+" "+participant.getUser().getFirstName());
            table.addCell(participant.getHorse().getName());
            table.addCell(participant.getRoundTime().toString());
            table.addCell(participant.getPoints().toString());
            place++;

        }
        document.add(table);
        document.close();
        return byteArrayOutputStream.toByteArray();

    }
}
