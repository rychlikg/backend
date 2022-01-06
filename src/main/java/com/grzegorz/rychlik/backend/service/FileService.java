package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.model.dao.Competition;
import com.grzegorz.rychlik.backend.model.dao.Contest;
import com.grzegorz.rychlik.backend.model.dao.Participant;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
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
        paragraph.add("Zawody: " + contest.getCompetition().getName());
        document.add(paragraph);
        Paragraph paragraph2 = new Paragraph();
        paragraph2.add("Konkurs: " + contest.getName());
        document.add(paragraph2);
        Table table = new Table(3);
        table.setWidth(new UnitValue(2,100));
        table.addHeaderCell("Miejsce");
        table.addHeaderCell("Zawodnik");
        table.addHeaderCell("Kon");
        List<Participant> participantsByContestId = participantService.getParticipantsByContestId(contestId);
        boolean isDone = false;
        if(!participantsByContestId.isEmpty()){
            Participant participant = participantsByContestId.get(0);
            Contest contest1 = participant.getContest();
            Paragraph paragraph3 = new Paragraph();
            paragraph3.add("Godzina rozpoczęcia: " + contest.getStart().toString());
            paragraph3.setTextAlignment(TextAlignment.RIGHT);
            document.add(paragraph3);
            Competition competition = contest1.getCompetition();
            if((contest1.getStart() != null &&  competition.getStartDate().isBefore(LocalDate.now())) ||
                    (competition.getStartDate().equals(LocalDate.now()) && contest.getStart().isBefore(LocalTime.now()))){
                table = new Table(5);
                table.setWidth(new UnitValue(2,100));
                table.addHeaderCell("Miejsce");
                table.addHeaderCell("Zawodnik");
                table.addHeaderCell("Kon");
                table.addHeaderCell("Czas");
                table.addHeaderCell("Punkty");
                isDone = true;
            }
        }
        Integer place = 1;
        for (Participant participant : participantsByContestId) {
            table.addCell(place.toString());
            table.addCell(participant.getUser().getLastName()+" "+participant.getUser().getFirstName());
            table.addCell(participant.getHorse().getName());
            if(isDone){
                table.addCell(participant.getRoundTime().toString());
                table.addCell(participant.getPoints().toString());
            }

            place++;

        }
        document.add(table);
        document.close();
        return byteArrayOutputStream.toByteArray();

    }
}
