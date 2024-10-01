import java.util.*;

class No {
    char caractere;
    No esquerda;
    No direita;

    No(char caractere) {
        this.caractere = caractere;
        this.esquerda = null;
        this.direita = null;
    }
}

class ArvoreCodificacaoMorse {
    private No raiz;

    public ArvoreCodificacaoMorse() {
        raiz = new No('\0');
    }

    public void inserir(String morse, char caractere) {
        No atual = raiz;
        for (char c : morse.toCharArray()) {
            if (c == '.') {
                if (atual.esquerda == null) {
                    atual.esquerda = new No('\0');
                }
                atual = atual.esquerda;
            } else if (c == '-') {
                if (atual.direita == null) {
                    atual.direita = new No('\0');
                }
                atual = atual.direita;
            }
        }
        atual.caractere = caractere;
    }

    public char buscar(String morse) {
        No atual = raiz;
        for (char c : morse.toCharArray()) {
            if (c == '.') {
                atual = atual.esquerda;
            } else if (c == '-') {
                atual = atual.direita;
            }
            if (atual == null) {
                return '\0';
            }
        }
        return atual.caractere;
    }

    public void inicializarArvore() {
        inserir(".-", 'A');
        inserir("-...", 'B');
        inserir("-.-.", 'C');
        inserir("-..", 'D');
        inserir(".", 'E');
        inserir("..-.", 'F');
        inserir("--.", 'G');
        inserir("....", 'H');
        inserir("..", 'I');
        inserir(".---", 'J');
        inserir("-.-", 'K');
        inserir(".-..", 'L');
        inserir("--", 'M');
        inserir("-.", 'N');
        inserir("---", 'O');
        inserir(".--.", 'P');
        inserir("--.-", 'Q');
        inserir(".-.", 'R');
        inserir("...", 'S');
        inserir("-", 'T');
        inserir("..-", 'U');
        inserir("...-", 'V');
        inserir(".--", 'W');
        inserir("-..-", 'X');
        inserir("-.--", 'Y');
        inserir("--..", 'Z');
        inserir("-----", '0');
        inserir(".----", '1');
        inserir("..---", '2');
        inserir("...--", '3');
        inserir("....-", '4');
        inserir(".....", '5');
        inserir("-....", '6');
        inserir("--...", '7');
        inserir("---..", '8');
        inserir("----.", '9');
    }

    public void exibirArvore() {
        exibirArvoreAuxiliar(raiz, "", "");
    }

    private void exibirArvoreAuxiliar(No no, String prefixo, String prefixoFilhos) {
        if (no == null) {
            return;
        }

        System.out.println(prefixo + (no.caractere == '\0' ? "•" : no.caractere));

        if (no.esquerda != null || no.direita != null) {
            exibirArvoreAuxiliar(no.esquerda, prefixoFilhos + "├── ", prefixoFilhos + "│   ");
            exibirArvoreAuxiliar(no.direita, prefixoFilhos + "└── ", prefixoFilhos + "    ");
        }
    }

    public String morseParaTexto(String morse) {
        StringBuilder texto = new StringBuilder();
        String[] palavras = morse.split("   ");
        for (String palavra : palavras) {
            String[] letras = palavra.split(" ");
            for (String letra : letras) {
                char c = buscar(letra);
                if (c != '\0') {
                    texto.append(c);
                }
            }
            texto.append(" ");
        }
        return texto.toString().trim();
    }

    public String textoParaMorse(String texto) {
        StringBuilder morse = new StringBuilder();
        for (char c : texto.toUpperCase().toCharArray()) {
            if (c == ' ') {
                morse.append("  ");
            } else {
                String codigo = encontrarCodigoMorse(raiz, c, "");
                if (!codigo.isEmpty()) {
                    morse.append(codigo).append(" ");
                }
            }
        }
        return morse.toString().trim();
    }

    private String encontrarCodigoMorse(No no, char alvo, String codigo) {
        if (no == null) {
            return "";
        }
        if (no.caractere == alvo) {
            return codigo;
        }
        String resultadoEsquerda = encontrarCodigoMorse(no.esquerda, alvo, codigo + ".");
        if (!resultadoEsquerda.isEmpty()) {
            return resultadoEsquerda;
        }
        return encontrarCodigoMorse(no.direita, alvo, codigo + "-");
    }
}

public class ConversorCodigoMorse {
    public static void main(String[] args) {
        ArvoreCodificacaoMorse arvore = new ArvoreCodificacaoMorse();
        arvore.inicializarArvore();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nConversor de Código Morse");
            System.out.println("1. Texto para Código Morse");
            System.out.println("2. Código Morse para Texto");
            System.out.println("3. Exibir Árvore de Código Morse");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            switch (escolha) {
                case 1:
                    System.out.print("Digite o texto: ");
                    String texto = scanner.nextLine();
                    System.out.println("Código Morse: " + arvore.textoParaMorse(texto));
                    break;
                case 2:
                    System.out.print("Digite o Código Morse (use espaço entre letras e '   ' entre palavras): ");
                    String morse = scanner.nextLine();
                    System.out.println("Texto: " + arvore.morseParaTexto(morse));
                    break;
                case 3:
                    arvore.exibirArvore();
                    break;
                case 4:
                    System.out.println("Até logo!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }
    }
}