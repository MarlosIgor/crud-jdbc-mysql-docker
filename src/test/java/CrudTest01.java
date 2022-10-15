import service.AnimeService;
import service.ProducerService;
import java.util.Scanner;

public class CrudTest01 {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int op;
        while (true) {
            menu();
            op = Integer.parseInt(SCANNER.nextLine());
            if (op == 0) break;
            switch (op) {
                case 1 -> {
                    producerMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    ProducerService.menu(op);
                }
                case 2 -> {
                    animeMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    AnimeService.menu(op);
                }
            }
        }
    }

    private static void menu() {
        System.out.println("Digite o número da sua operação");
        System.out.println("1. Producer");
        System.out.println("2. Anime");
        System.out.println("0. Saíndo");
    }

    private static void producerMenu() {
        System.out.println("Digite o número da sua operação");
        System.out.println("1. Procurar producer");
        System.out.println("2. Excluir producer");
        System.out.println("3. Salvar producer");
        System.out.println("4. Atualizar producer");
        System.out.println("9. Saíndo");
    }

    private static void animeMenu() {
        System.out.println("Digite o número da sua operação");
        System.out.println("1. Procurar anime");
        System.out.println("2. Excluir anime");
        System.out.println("3. Salvar anime");
        System.out.println("4. Atualizar anime");
        System.out.println("9. Saíndo");
    }
}

