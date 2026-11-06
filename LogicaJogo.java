import java.util.Random;

public class LogicaJogo {
    private int num;

    public LogicaJogo(){
        Random random = new Random();
        num = random.nextInt(100) + 1;
    }

    public String verificarPalpite (int palpite) {
        if (num > palpite) {
            return "Tá baixo";
        } else if (num < palpite) {
            return "Tá alto";
        } else {
            return "Na mosca!";
        }
}}