package com.br.ipad.isc.impressao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.Util;

/**
 * Métodos de uso comum no momento de impressão
 * para todas as empresas
 * 
 * @author Amelia Pessoa
 *
 */
public class Impressao {

	protected StringBuilder buffer;
	protected ImovelConta imovel;
	protected Fachada fachada = Fachada.getInstance();
	
	protected StringBuilder dividirLinha(int fonte, int tamanhoFonte, int x, int y, String texto, int tamanhoLinha,
			int deslocarPorLinha) {
		StringBuilder retorno = new StringBuilder();
		int contador = 0;
		int i;
		for (i = 0; i < texto.length(); i += tamanhoLinha) {
			contador += tamanhoLinha;
			if (contador > texto.length()) {
				retorno.append("T " + fonte + " " + tamanhoFonte + " " + x + " " + y + " "
						+ texto.substring(i, texto.length()).trim() + "\n");
			} else {
				retorno.append("T " + fonte + " " + tamanhoFonte + " " + x + " " + y + " " 
						+ texto.substring(i, contador).trim() + "\n");
			}
			y += deslocarPorLinha;
		}
		return retorno;
	}

	protected StringBuilder formarLinha(int font, int tamanhoFonte, int x, int y, StringBuilder texto, int adicionarColuna,
			int adicionarLinha) {
		
		return new StringBuilder("T " + font + " " + tamanhoFonte + " " + (x + adicionarColuna) + " " + 
				(y + adicionarLinha) + " " + texto + "\n");
	}

	protected StringBuilder formarLinha(int font, int tamanhoFonte, int x, int y, String texto, int adicionarColuna,
			int adicionarLinha) {
		
		return new StringBuilder("T " + font + " " + tamanhoFonte + " " + (x + adicionarColuna) + " " + 
				(y + adicionarLinha) + " " + texto + "\n");
	}
	
	
	/**
     * Verifica se a string fornecido é null ou vazia comparando ela com ""
     * depois de fazer um trim()
     *
     * @param str
     *            string a ser verificada
     * @return Retorna true se a é null ou igual a "", caso contrario retorna
     *         false
     */
    protected boolean isNullOrEmpty(final String str) {
	    boolean retorno = false;
	    if (str == null || str.trim().equals("")){
	    	retorno =  true;
	    }

	    return retorno;
    }
    
    /**
     * Completa com zeros a direita da string respeitando o tamanho maximo da
     * string
     */
    protected StringBuilder zerosDireita(final int tamanhoMaximo,
        final StringBuilder valor) {
    	StringBuilder zeros = new StringBuilder();
	
	    for (int i = 0; i < (tamanhoMaximo - valor.length()); i++)
	        zeros.append("0");
	
	    return valor.append(zeros);
    }
    
    /**
     * Verifica se o valor double é igual a Constantes.NULO_DOUBLE, Indicando
     * que o valor é vazio ou inválido dentro do arquivo de carga
     *
     * @return Retorna "--" caso comparação com a constante seja verdade, caso
     *         contrario retorna conversão do valor para string
     */
    protected String VerificarString(BigDecimal valor) {
        if (valor == null )
            return "--";

        return valor+"";
    }
    
	/**
     * Retorna uma substring de uma String respeitando um tamanho maximo
     *
     * @param str
     *            String a ser cortada
     * @param inicio
     *            Posicao de inicio na String
     * @param tamanho
     *            Tamanho da substring
     * @return Retorna uma substring de S com tamanho maximo de length
     */
    protected String substring(final String str, final int inicio,
        final int tamanho) {
    	
    	String retorno = "";
    	boolean campoNulo = isNullOrEmpty(str);
    	if(!campoNulo && !(inicio >= str.length())){
    	
		    if (str.length() < tamanho){
		    	retorno = str;
		    }else{	
			    retorno = str.substring(inicio, Math.min(inicio + tamanho,
			        str.length() - 1));
	    	}
    	}
    	return retorno;
    }

    protected final String formatarData(final Date data) {
    	StringBuilder sb = new StringBuilder(25);

	    if (data != null) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(data);
	
	        int day = calendar.get(Calendar.DAY_OF_MONTH);
	        if (day < 10)
	        sb.append("0");
	
	        sb.append(day);
	
	        sb.append("/");
	
	        int month = calendar.get(Calendar.MONTH) + 1;
	        if (month < 10)
	        sb.append("0");
	
	        sb.append(month);
	
	        sb.append("/");
	
	        sb.append(calendar.get(Calendar.YEAR));
	    }
	
	    return sb.toString();
    }
    
    /**
     * Forma um telefone no formato "YYXXXXZZZZ" para YY XXXX-ZZZZ telefone deve
     * ter 10 digitos
     *
     * @return Retorna o telefone formatado ou o telefone sem formatar caso este
     *         tenha menos de 10 digitos
     */
    protected StringBuilder formatarTelefone(String telefone) {
	    if (telefone == null)
	        return new StringBuilder(telefone);
	
	    if (telefone.length() < 10)
	    	return new StringBuilder(telefone);
	
	    return new StringBuilder(telefone.substring(0, 2) + " " + telefone.substring(2, 6) + "-"
	        + telefone.substring(6));
    }

    /**
     * Formata o valor double em um formato monetario: "x.xxx.xxx,xx"
     */
    protected StringBuilder formatarValorMonetario(final double valor) {
	    double valorArredondado = Util.arredondar(valor, 2);
	    int inteiro = (int) valorArredondado;
	    double decimal = Util.arredondar(valorArredondado - (int) inteiro, 2);
	
	    StringBuilder inteiroString = new StringBuilder(inteiro);
	    StringBuilder comPontoInvertido = new StringBuilder(); 
	    int contador = 0;
	
	    // Agrupamos de 3 em 3
	    for (int i = inteiroString.length() - 1; i >= 0; i--) {
	        contador++;
	        comPontoInvertido.append(inteiroString.charAt(i));
	
	        if (contador % 3 == 0 && i != 0)
	        comPontoInvertido.append(".");
	    }
	
	    StringBuilder comPonto = new StringBuilder();
	
	    // Invertemos
	    for (int i = comPontoInvertido.length() - 1; i >= 0; i--) {
	        contador++;
	        comPonto.append(comPontoInvertido.charAt(i));
	    }
	
	    StringBuilder decimalString = new StringBuilder(Double.toString(decimal));
	    decimalString = new StringBuilder(decimalString.substring(2, decimalString.length()));
	
	    // Colocamos a virgula
	    return comPonto.append("," + zerosDireita(2, decimalString));
    }
    
    /**
     * Adiciona impressao de texto na posicao <x,y> gerando linhas com alturas
     * especificadas quando quantidade de caracteres passar do limite
     */
    protected void appendTextos(int fonte, int tamanho, int x, int y,
        String texto, int caracteresPorLinha, int alturaLinha) {
    	for (int i = 0; i < texto.length(); i += caracteresPorLinha, y += alturaLinha) {
        appendTexto(fonte, tamanho, x, y, false, texto.substring(i, Math
            .min(i + caracteresPorLinha, texto.length())));
    	}
    }

    /**
     * Adiciona impressao de texto na posicao <x,y> onde cada posicao do array é
     * uma linha quantidade de caracteres passar do limite
     */
    protected void appendTextos(int fonte, int tamanho, int x, int y,
        String[] texto, int alturaLinha) {
    	
    	for (int i = 0; i < texto.length; i++, y += alturaLinha) {
    		appendTexto(fonte, tamanho, x, y, false, texto[i]);
    	}
    }
    
    /**
     * Adiciona impressao de texto com fonte e tamanho especificados, na posicao
     * <x,y>
     */
    protected void appendTexto(int fonte, int tamanho, int x, int y,
        boolean direita, StringBuilder texto) {
    	
	    if (direita) {
	        buffer.append("RIGHT ");
	        buffer.append(x);
	        buffer.append("\n");
	    }
	
	    buffer.append("TEXT ");
	    buffer.append(fonte);
	    buffer.append(" ");
	    buffer.append(tamanho);
	    buffer.append(" ");
	    buffer.append(x);
	    buffer.append(" ");
	    buffer.append(y);
	    buffer.append(" ");
	    buffer.append(texto.toString().trim());
	    buffer.append("\n");
	
	    // Volta alinhar texto para esquerda
	    if (direita) {
	        buffer.append("LEFT\n");
	    }
    }
    
    /**
     * Adiciona impressao de texto com fonte e tamanho especificados, na posicao
     * <x,y>
     */
    protected void appendTexto(int fonte, int tamanho, int x, int y,
        boolean direita, String texto) {
    	
	    if (direita) {
	        buffer.append("RIGHT ");
	        buffer.append(x);
	        buffer.append("\n");
	    }
	
	    buffer.append("TEXT ");
	    buffer.append(fonte);
	    buffer.append(" ");
	    buffer.append(tamanho);
	    buffer.append(" ");
	    buffer.append(x);
	    buffer.append(" ");
	    buffer.append(y);
	    buffer.append(" ");
	    buffer.append(texto.trim());
	    buffer.append("\n");
	
	    // Volta alinhar texto para esquerda
	    if (direita) {
	        buffer.append("LEFT\n");
	    }
    }
    
    /**
     * Adicionar um comando de texto no buffer com fonte 7, tamanho 0
     */
    protected void appendTexto70(int x, int y, StringBuilder texto) {
    	appendTexto(7, 0, x, y, false, texto);
    }

    /**
     * Adicionar um comando de texto no buffer com fonte 7, tamanho 0
     */
    protected void appendTexto70(int x, int y, boolean direita, StringBuilder texto) {
    	appendTexto(7, 0, x, y, direita, texto);
    }
    
    /**
     * Adicionar um comando de texto no buffer com fonte 7, tamanho 0
     */
    protected void appendTexto70(int x, int y, String texto) {
    	appendTexto(7, 0, x, y, false, texto);
    }
    
    /**
     * Adicionar um comando de texto no buffer com fonte 0, tamanho 2
     */
    protected void appendTexto20(int x, int y, String texto) {
    	appendTexto(0, 2, x, y, false, texto);
    }


    /**
     * Adicionar um comando de texto no buffer com fonte 7, tamanho 0
     */
    protected void appendTexto70(int x, int y, boolean direita, String texto) {
    	appendTexto(7, 0, x, y, direita, texto);
    }
    
    /**
     * Adicionar o desenho de uma linha ao final do buffer
     */
    protected void appendLinha(int xIni, int yIni, int xFim, int yFim, float width) {
	    buffer.append("LINE ");
	    buffer.append(xIni);
	    buffer.append(" ");
	    buffer.append(yIni);
	    buffer.append(" ");
	    buffer.append(xFim);
	    buffer.append(" ");
	    buffer.append(yFim);
	    buffer.append(" ");
	    buffer.append(width);
	    buffer.append("\n");
    }

    /**
     * Adicionar um texto ao final do buffer
     */
    protected void appendTexto(String texto) {
    	buffer.append(texto);
    }

    /**
     * Adicionar um comando que imprimi codigo de barras no final do buffer
     */
    protected void appendBarcode(int x, int y, String barcode) {
	    buffer.append("BARCODE I2OF5 0.24 2 13 ");
	    buffer.append(x);
	    buffer.append(" ");
	    buffer.append(y);
	    buffer.append(" ");
	    buffer.append(barcode);
	    buffer.append("\n");
    }
    
    /**
     * Corta o endereço do cliente em duas linhas caso a string com o endereço
     * tenha mais que 62 caracteres
     */
    protected static String[] cortarEndereco(String endereco) {
	    final int caracteresPorLinha = 62;
	    String[] retorno = null;
	
	    if (endereco.length() <= caracteresPorLinha) {
	        retorno = new String[1];
	        retorno[0] = endereco;
	    } else {
	        int ultimoEspaco = caracteresPorLinha;
	        while (endereco.charAt(ultimoEspaco) != ' ')
	        ultimoEspaco--;
	
	        retorno = new String[2];
	        retorno[0] = endereco.substring(0, ultimoEspaco);
	        retorno[1] = endereco.substring(ultimoEspaco + 1);
	    }
	
	    return retorno;
    }

    // Comando que faz a impressão
    protected String comandoImpressao(){
    	return "FORM\n" + "PRINT ";
    }
}