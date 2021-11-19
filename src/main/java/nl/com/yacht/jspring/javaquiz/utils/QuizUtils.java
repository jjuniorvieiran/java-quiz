package nl.com.yacht.jspring.javaquiz.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import nl.com.yacht.jspring.javaquiz.models.Question;
import nl.com.yacht.jspring.javaquiz.models.User;

public class QuizUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuizUtils.class);

    public static void handleTime(User user) {

        long diff = user.getEndDateTime().getTime() - user.getStartDateTime().getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);

        while (diffSeconds > 59) {
            diffSeconds = diffSeconds - 60;
        }

        user.setTimeTest(diff);
        user.setTimeDisplayGrid("" + diffMinutes + " m: " + diffSeconds + " s");
    }

    public static List<Question> getHandleQuestion(List<Question> givenList) {

        Random rand = new Random();
        List<Question> randomList = new ArrayList<Question>();
        int numberOfElements = 5;
        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            Question randomElement = givenList.get(randomIndex);
            randomList.add(randomElement);
            givenList.remove(randomIndex);
        }

        return randomList;
    }

    public static void validateAnswer(Question question, User user) {
        int numberOfCheckBoxSelected = 0;

        if (question.isCheckAnswer1())
            numberOfCheckBoxSelected++;
        if (question.isCheckAnswer2())
            numberOfCheckBoxSelected++;
        if (question.isCheckAnswer3())
            numberOfCheckBoxSelected++;
        if (question.isCheckAnswer4())
            numberOfCheckBoxSelected++;

        if (numberOfCheckBoxSelected == 1) {
            if (question.getAnswerCorrect().equalsIgnoreCase("1") && question.isCheckAnswer1()) {
                user.setUserAnswerCorrect(user.getUserAnswerCorrect() + 1);
            } else if (question.getAnswerCorrect().equalsIgnoreCase("2") && question.isCheckAnswer2()) {

                user.setUserAnswerCorrect(user.getUserAnswerCorrect() + 1);
            } else if (question.getAnswerCorrect().equalsIgnoreCase("3") && question.isCheckAnswer3()) {

                user.setUserAnswerCorrect(user.getUserAnswerCorrect() + 1);
            } else if (question.getAnswerCorrect().equalsIgnoreCase("4") && question.isCheckAnswer4()) {
                user.setUserAnswerCorrect(user.getUserAnswerCorrect() + 1);
            }
        }
    }

    public static List<Map<String, String>> loadAvailableQuizz() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(ResourceUtils.getFile("classpath:quizz.json"));
            ObjectReader reader = mapper.readerFor(new TypeReference<List<Map<String, String>>>() {});
            return reader.readValue(rootNode);
        } catch (IOException e) {
            LOGGER.error("Error on load quizz.", e);
            return Collections. <Map<String, String>>emptyList();
        }
    }
}
