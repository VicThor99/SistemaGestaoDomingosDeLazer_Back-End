package br.com.domingosdelazer.SistemaGestao.service.impl;

import br.com.domingosdelazer.SistemaGestao.entity.Aluno;
import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.repository.AlunoRepository;
import br.com.domingosdelazer.SistemaGestao.repository.DataAulaRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JasperServiceImpl {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private DataAulaRepository dataAulaRepository;

    public byte[] preencherJasperCrachas(String domingo, String codigo, String serie, String sala, Boolean ativos) throws JRException, FileNotFoundException {
        StringBuilder alunosJSON = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
        DataAula entregaSacolinha = dataAulaRepository.getEntregaSacolinha();
        List<Aluno> alunos;

        if (!StringUtils.isEmpty(domingo)) {
            alunos = ativos ? alunoRepository.getAlunosAtivosPorDomingo(domingo) : alunoRepository.getAlunosPorDomingo(domingo);
        } else if (!StringUtils.isEmpty(serie)) {
            alunos = ativos ? alunoRepository.getAlunosAtivosPorSerie(serie) : alunoRepository.getAlunosPorSerie(serie);
        } else if (!StringUtils.isEmpty(sala)) {
            alunos = ativos ? alunoRepository.getAlunosAtivosPorSala(sala) : alunoRepository.getAlunosPorSala(sala);
        } else if (!StringUtils.isEmpty(codigo)) {
            alunos = alunoRepository.getAlunosPorCodigo(codigo);
        } else {
            alunos = ativos ? alunoRepository.getAlunosAtivos() : alunoRepository.findAll();
        }

        alunosJSON.append("[");

        for (int i = 0; i < alunos.size(); i++) {
            alunosJSON.append(i == 0 ? "{" : ",{");

            List<DataAula> datas = dataAulaRepository.getAulasPorDomingo((alunos.get(i).getCodigo().startsWith("1") ? "A" : "B"));

            alunosJSON.append("\"ANO\":")
                    .append(sdfAno.format(new Date()))
                    .append(",");
            alunosJSON.append("\"ENTREGA_SACOLINHAS\":\"")
                    .append(sdf.format(entregaSacolinha.getDataAula()))
                    .append("\",");
            alunosJSON.append("\"DATA_FEVEREIRO_1\":\"")
                    .append(sdf.format(datas.get(0).getDataAula()))
                    .append("\",");
            alunosJSON.append("\"DATA_MARCO_1\":\"")
                    .append(sdf.format(datas.get(1).getDataAula()))
                    .append("\",");
            alunosJSON.append("\"DATA_ABRIL_1\":\"")
                    .append(sdf.format(datas.get(2).getDataAula()))
                    .append("\",");
            alunosJSON.append("\"DATA_MAIO_1\":\"")
                    .append(sdf.format(datas.get(3).getDataAula()))
                    .append("\",");
            alunosJSON.append("\"DATA_JUNHO_1\":\"")
                    .append(sdf.format(datas.get(4).getDataAula()))
                    .append("\",");
            alunosJSON.append("\"DATA_AGOSTO_1\":\"")
                    .append(sdf.format(datas.get(5).getDataAula()))
                    .append("\",");
            alunosJSON.append("\"DATA_SETEMBRO_1\":\"")
                    .append(sdf.format(datas.get(6).getDataAula()))
                    .append("\",");
            alunosJSON.append("\"DATA_OUTUBRO_1\":\"")
                    .append(sdf.format(datas.get(7).getDataAula()))
                    .append("\",");
            alunosJSON.append("\"DATA_NOVEMBRO_1\":\"")
                    .append(sdf.format(datas.get(8).getDataAula()))
                    .append("\",");
            alunosJSON.append("\"DOMINGO_1\":\"DOM ")
                    .append((alunos.get(i).getCodigo().startsWith("1") ? "A" : "B"))
                    .append("\",");
            alunosJSON.append("\"NOME_1\":\"")
                    .append(alunos.get(i).getNome().split(" ")[0])
                    .append("\",");
            alunosJSON.append("\"SOBRENOME_1\":\"")
                    .append(alunos.get(i).getNome()
                            .replace(alunos.get(i).getNome().split(" ")[0] + " ", ""))
                    .append("\",");
            alunosJSON.append("\"SERIE_1\":\"")
                    .append(alunos.get(i).getSerie().getSerie())
                    .append("\",");
            alunosJSON.append("\"SALA_1\":")
                    .append(Integer.parseInt(alunos.get(i).getSerie().getSala().split(" ")[1]))
                    .append(",");
            alunosJSON.append("\"CODIGO_DE_BARRAS_1\":")
                    .append(Integer.parseInt(alunos.get(i).getCodigo()))
                    .append(",");
            alunosJSON.append("\"DATA_NASCIMENTO_1\":\"")
                    .append(sdf.format(alunos.get(i).getNascimento()))
                    .append("\",");
            alunosJSON.append("\"SEXO_1\":\"")
                    .append(alunos.get(i).getSexo().equalsIgnoreCase("F")
                            ? "FEMININO" : "MASCULINO")
                    .append("\",");
            alunosJSON.append("\"RESPONSAVEL_1\":\"")
                    .append(StringUtils.isEmpty(alunos.get(i).getNomeResponsavel())
                            ? "" : alunos.get(i).getNomeResponsavel())
                    .append("\",");
            alunosJSON.append("\"TELEFONE_1\":\"")
                    .append(StringUtils.isEmpty(alunos.get(i).getTelefoneResponsavel())
                            ? "" : alunos.get(i).getTelefoneResponsavel())
                    .append("\",");
            alunosJSON.append("\"EMAIL_1\":\"")
                    .append(StringUtils.isEmpty(alunos.get(i).getEmailResponsavel())
                            ? "" : alunos.get(i).getEmailResponsavel())
                    .append("\",");
            alunosJSON.append("\"ENDERECO_1\":\"")
                    .append(StringUtils.isEmpty(alunos.get(i).getEndereco())
                            ? "" : alunos.get(i).getEndereco())
                    .append("\",");
            alunosJSON.append("\"SAIR_SOZINHO_1\":\"")
                    .append(alunos.get(i).getSairSozinho() ? "SIM" : "NÃO")
                    .append("\",");
            alunosJSON.append("\"SAIR_SOZINHO_1_PODE\":\"")
                    .append(alunos.get(i).getSairSozinho() ? "X" : "")
                    .append("\",");
            alunosJSON.append("\"SAIR_SOZINHO_1_NAO_PODE\":\"")
                    .append(alunos.get(i).getSairSozinho() ? "" : "X")
                    .append("\",");

            if ((i + 1) < alunos.size()) {
                datas = dataAulaRepository.getAulasPorDomingo((alunos.get(i + 1).getCodigo().startsWith("1") ? "A" : "B"));

                alunosJSON.append("\"DATA_FEVEREIRO_2\":\"")
                        .append(sdf.format(datas.get(0).getDataAula()))
                        .append("\",");
                alunosJSON.append("\"DATA_MARCO_2\":\"")
                        .append(sdf.format(datas.get(1).getDataAula()))
                        .append("\",");
                alunosJSON.append("\"DATA_ABRIL_2\":\"")
                        .append(sdf.format(datas.get(2).getDataAula()))
                        .append("\",");
                alunosJSON.append("\"DATA_MAIO_2\":\"")
                        .append(sdf.format(datas.get(3).getDataAula()))
                        .append("\",");
                alunosJSON.append("\"DATA_JUNHO_2\":\"")
                        .append(sdf.format(datas.get(4).getDataAula()))
                        .append("\",");
                alunosJSON.append("\"DATA_AGOSTO_2\":\"")
                        .append(sdf.format(datas.get(5).getDataAula()))
                        .append("\",");
                alunosJSON.append("\"DATA_SETEMBRO_2\":\"")
                        .append(sdf.format(datas.get(6).getDataAula()))
                        .append("\",");
                alunosJSON.append("\"DATA_OUTUBRO_2\":\"")
                        .append(sdf.format(datas.get(7).getDataAula()))
                        .append("\",");
                alunosJSON.append("\"DATA_NOVEMBRO_2\":\"")
                        .append(sdf.format(datas.get(8).getDataAula()))
                        .append("\",");
                alunosJSON.append("\"DOMINGO_2\":\"DOM ")
                        .append((alunos.get(i + 1).getCodigo().startsWith("1") ? "A" : "B"))
                        .append("\",");
                alunosJSON.append("\"NOME_2\":\"")
                        .append(alunos.get(i + 1).getNome().split(" ")[0])
                        .append("\",");
                alunosJSON.append("\"SOBRENOME_2\":\"")
                        .append(alunos.get(i + 1).getNome()
                                .replace(alunos.get(i + 1).getNome().split(" ")[0] + " ", ""))
                        .append("\",");
                alunosJSON.append("\"SERIE_2\":\"")
                        .append(alunos.get(i + 1).getSerie().getSerie())
                        .append("\",");
                alunosJSON.append("\"SALA_2\":")
                        .append(Integer.parseInt(alunos.get(i + 1).getSerie().getSala().split(" ")[1]))
                        .append(",");
                alunosJSON.append("\"CODIGO_DE_BARRAS_2\":")
                        .append(Integer.parseInt(alunos.get(i + 1).getCodigo()))
                        .append(",");
                alunosJSON.append("\"DATA_NASCIMENTO_2\":\"")
                        .append(sdf.format(alunos.get(i + 1).getNascimento()))
                        .append("\",");
                alunosJSON.append("\"SEXO_2\":\"")
                        .append(alunos.get(i + 1).getSexo().equalsIgnoreCase("F")
                                ? "FEMININO" : "MASCULINO")
                        .append("\",");
                alunosJSON.append("\"RESPONSAVEL_2\":\"")
                        .append(StringUtils.isEmpty(alunos.get(i + 1).getNomeResponsavel())
                                ? "" : alunos.get(i + 1).getNomeResponsavel())
                        .append("\",");
                alunosJSON.append("\"TELEFONE_2\":\"")
                        .append(StringUtils.isEmpty(alunos.get(i + 1).getTelefoneResponsavel())
                                ? "" : alunos.get(i + 1).getTelefoneResponsavel())
                        .append("\",");
                alunosJSON.append("\"EMAIL_2\":\"")
                        .append(StringUtils.isEmpty(alunos.get(i + 1).getEmailResponsavel())
                                ? "" : alunos.get(i + 1).getEmailResponsavel())
                        .append("\",");
                alunosJSON.append("\"ENDERECO_2\":\"")
                        .append(StringUtils.isEmpty(alunos.get(i + 1).getEndereco())
                                ? "" : alunos.get(i + 1).getEndereco())
                        .append("\",");
                alunosJSON.append("\"SAIR_SOZINHO_2\":\"")
                        .append(alunos.get(i + 1).getSairSozinho() ? "SIM" : "NÃO")
                        .append("\",");
                alunosJSON.append("\"SAIR_SOZINHO_2_PODE\":\"")
                        .append(alunos.get(i + 1).getSairSozinho() ? "X" : "")
                        .append("\",");
                alunosJSON.append("\"SAIR_SOZINHO_2_NAO_PODE\":\"")
                        .append(alunos.get(i + 1).getSairSozinho() ? "" : "X")
                        .append("\"");
            } else {
                alunosJSON.append("\"NOME_2\":\"\",");
                alunosJSON.append("\"SOBRENOME_2\":\"\",");
                alunosJSON.append("\"SERIE_2\":\"\",");
                alunosJSON.append("\"SALA_2\":0,");
                alunosJSON.append("\"CODIGO_DE_BARRAS_2\":0,");
                alunosJSON.append("\"DATA_NASCIMENTO_2\":\"\",");
                alunosJSON.append("\"SEXO_2\":\"\",");
                alunosJSON.append("\"RESPONSAVEL_2\":\"\",");
                alunosJSON.append("\"TELEFONE_2\":\"\",");
                alunosJSON.append("\"EMAIL_2\":\"\",");
                alunosJSON.append("\"ENDERECO_2\":\"\",");
                alunosJSON.append("\"SAIR_SOZINHO_2\":\"\",");
                alunosJSON.append("\"SAIR_SOZINHO_2_PODE\":\"\",");
                alunosJSON.append("\"SAIR_SOZINHO_2_NAO_PODE\":\"\"");
            }

            alunosJSON.append("}");
            i++;
        }

        alunosJSON.append("]");

        File file = ResourceUtils.getFile("classpath:CrachasDomingoDeLazer.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = new HashMap<>();

        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(alunosJSON.toString().getBytes());
        JsonDataSource ds = new JsonDataSource(jsonDataStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] preencherJasperMatriculas(String domingo, String codigo, String serie, String sala) throws JRException, FileNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
        List<Aluno> alunos;

        if (!StringUtils.isEmpty(domingo)) {
            alunos = alunoRepository.getAlunosPorDomingo(domingo);
        } else if (!StringUtils.isEmpty(serie)) {
            alunos = alunoRepository.getAlunosPorSerie(serie);
        } else if (!StringUtils.isEmpty(sala)) {
            alunos = alunoRepository.getAlunosPorSala(sala);
        } else if (!StringUtils.isEmpty(codigo)) {
            alunos = alunoRepository.getAlunosPorCodigo(codigo);
        } else {
            alunos = alunoRepository.findAll();
        }

        StringBuilder alunosJSON = new StringBuilder();
        alunosJSON.append("[");

        for (int i = 0; i < alunos.size(); i++) {
            alunosJSON.append(i == 0 ? "{" : ",{");

            DataAula primeiraAula = dataAulaRepository.getPrimeiraAula(alunos.get(i).getSerie().getDomingo());

            alunosJSON.append("\"ANO\":")
                    .append(sdfAno.format(new Date()))
                    .append(",");
            alunosJSON.append("\"DATA_PRIMEIRA_AULA\":\"")
                    .append(sdf.format(primeiraAula.getDataAula()))
                    .append("\",");
            alunosJSON.append("\"NOME\":\"")
                    .append(alunos.get(i).getNome())
                    .append("\",");
            alunosJSON.append("\"TURMA\":\"DOM-")
                    .append(alunos.get(i).getSerie().getDomingo())
                    .append("\",");
            alunosJSON.append("\"SALA\":")
                    .append(alunos.get(i).getSerie().getSala().split(" ")[1])
                    .append(",");
            alunosJSON.append("\"SERIE\":\"")
                    .append(alunos.get(i).getSerie().getSerie())
                    .append("\",");
            alunosJSON.append("\"CODIGO\":\"")
                    .append(alunos.get(i).getCodigo())
                    .append("\",");
            alunosJSON.append("\"NASCIMENTO\":\"")
                    .append(sdf.format(alunos.get(i).getNascimento()))
                    .append("\"");

            alunosJSON.append("}");
        }

        alunosJSON.append("]");

        File file = ResourceUtils.getFile("classpath:MatriculaDomingoDeLazer.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = new HashMap<>();

        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(alunosJSON.toString().getBytes());
        JsonDataSource ds = new JsonDataSource(jsonDataStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] preencherJasperProtocolos(String domingo, String codigo, String serie, String sala, Boolean ativos) throws JRException, FileNotFoundException {
        StringBuilder alunosJSON = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
        DataAula entregaSacolinha = dataAulaRepository.getEntregaSacolinha();
        List<Aluno> alunos;

        if (!StringUtils.isEmpty(domingo)) {
            alunos = ativos ? alunoRepository.getAlunosAtivosPorDomingo(domingo) : alunoRepository.getAlunosPorDomingo(domingo);
        } else if (!StringUtils.isEmpty(serie)) {
            alunos = ativos ? alunoRepository.getAlunosAtivosPorSerie(serie) : alunoRepository.getAlunosPorSerie(serie);
        } else if (!StringUtils.isEmpty(sala)) {
            alunos = ativos ? alunoRepository.getAlunosAtivosPorSala(sala) : alunoRepository.getAlunosPorSala(sala);
        } else if (!StringUtils.isEmpty(codigo)) {
            alunos = alunoRepository.getAlunosPorCodigo(codigo);
        } else {
            alunos = ativos ? alunoRepository.getAlunosAtivos() : alunoRepository.findAll();
        }

        alunosJSON.append("[");

        for (int i = 0; i < alunos.size(); i++) {
            alunosJSON.append(i == 0 ? "{" : ",{");

            alunosJSON.append("\"ENTREGA_SACOLINHA\":\"")
                    .append(sdf.format(entregaSacolinha.getDataAula()))
                    .append("\",");
            alunosJSON.append("\"ANO\":\"")
                    .append(sdfAno.format(new Date()))
                    .append("\",");
            alunosJSON.append("\"DOMINGO_1\":\"DOM-")
                    .append(alunos.get(i).getSerie().getDomingo())
                    .append("\",");
            alunosJSON.append("\"SACOLINHA_1\":\"")
                    .append(StringUtils.isEmpty(alunos.get(i).getNumeroSacolinha()) ? "" : alunos.get(i).getNumeroSacolinha())
                    .append("\",");
            alunosJSON.append("\"NOME_1\":\"")
                    .append(alunos.get(i).getNome())
                    .append("\",");
            alunosJSON.append("\"SERIE_1\":\"")
                    .append(alunos.get(i).getSerie().getSerie())
                    .append("\",");
            alunosJSON.append("\"SALA_1\":\"")
                    .append(alunos.get(i).getSerie().getSala())
                    .append("\",");
            alunosJSON.append("\"CRACHA_1\":\"")
                    .append(alunos.get(i).getCodigo())
                    .append("\",");
            alunosJSON.append("\"IDADE_1\":\"")
                    .append(calculaIdade(alunos.get(i).getNascimento()))
                    .append("\",");
            alunosJSON.append("\"SEXO_1\":\"")
                    .append(alunos.get(i).getSexo())
                    .append("\",");
            alunosJSON.append("\"CALCADO_1\":\"")
                    .append(StringUtils.isEmpty(alunos.get(i).getSapato()) ? "" : alunos.get(i).getSapato())
                    .append("\",");
            alunosJSON.append("\"BLUSA_1\":\"")
                    .append(StringUtils.isEmpty(alunos.get(i).getCamisa()) ? "" : alunos.get(i).getCamisa())
                    .append("\",");
            alunosJSON.append("\"CALCA_1\":\"")
                    .append(StringUtils.isEmpty(alunos.get(i).getCalca()) ? "" : alunos.get(i).getCalca())
                    .append("\"");

            i++;
            if (i < alunos.size()) {
                alunosJSON.append(",\"DOMINGO_2\":\"DOM-")
                        .append(alunos.get(i).getSerie().getDomingo())
                        .append("\",");
                alunosJSON.append("\"SACOLINHA_2\":\"")
                        .append(StringUtils.isEmpty(alunos.get(i).getNumeroSacolinha()) ? "" : alunos.get(i).getNumeroSacolinha())
                        .append("\",");
                alunosJSON.append("\"NOME_2\":\"")
                        .append(alunos.get(i).getNome())
                        .append("\",");
                alunosJSON.append("\"SERIE_2\":\"")
                        .append(alunos.get(i).getSerie().getSerie())
                        .append("\",");
                alunosJSON.append("\"SALA_2\":\"")
                        .append(alunos.get(i).getSerie().getSala())
                        .append("\",");
                alunosJSON.append("\"CRACHA_2\":\"")
                        .append(alunos.get(i).getCodigo())
                        .append("\",");
                alunosJSON.append("\"IDADE_2\":\"")
                        .append(calculaIdade(alunos.get(i).getNascimento()))
                        .append("\",");
                alunosJSON.append("\"SEXO_2\":\"")
                        .append(alunos.get(i).getSexo())
                        .append("\",");
                alunosJSON.append("\"CALCADO_2\":")
                        .append(StringUtils.isEmpty(alunos.get(i).getSapato()) ? "" : alunos.get(i).getSapato())
                        .append(",");
                alunosJSON.append("\"BLUSA_2\":\"")
                        .append(StringUtils.isEmpty(alunos.get(i).getCamisa()) ? "" : alunos.get(i).getCamisa())
                        .append("\",");
                alunosJSON.append("\"CALCA_2\":\"")
                        .append(StringUtils.isEmpty(alunos.get(i).getCalca()) ? "" : alunos.get(i).getCalca())
                        .append("\"");
            } else {

                alunosJSON.append(",\"DOMINGO_2\":\"\",");
                alunosJSON.append("\"SACOLINHA_2\":\"\",");
                alunosJSON.append("\"NOME_2\":\"\",");
                alunosJSON.append("\"SERIE_2\":\"\",");
                alunosJSON.append("\"SALA_2\":\"\",");
                alunosJSON.append("\"CRACHA_2\":\"\",");
                alunosJSON.append("\"IDADE_2\":\"\",");
                alunosJSON.append("\"SEXO_2\":\"\",");
                alunosJSON.append("\"CALCADO_2\":\"\",");
                alunosJSON.append("\"BLUSA_2\":\"\",");
                alunosJSON.append("\"CALCA_2\":\"\"");
            }

            i++;
            if (i < alunos.size()) {
                alunosJSON.append(",\"DOMINGO_3\":\"DOM-")
                        .append(alunos.get(i).getSerie().getDomingo())
                        .append("\",");
                alunosJSON.append("\"SACOLINHA_3\":\"")
                        .append(StringUtils.isEmpty(alunos.get(i).getNumeroSacolinha()) ? "" : alunos.get(i).getNumeroSacolinha())
                        .append("\",");
                alunosJSON.append("\"NOME_3\":\"")
                        .append(alunos.get(i).getNome())
                        .append("\",");
                alunosJSON.append("\"SERIE_3\":\"")
                        .append(alunos.get(i).getSerie().getSerie())
                        .append("\",");
                alunosJSON.append("\"SALA_3\":\"")
                        .append(alunos.get(i).getSerie().getSala())
                        .append("\",");
                alunosJSON.append("\"CRACHA_3\":\"")
                        .append(alunos.get(i).getCodigo())
                        .append("\",");
                alunosJSON.append("\"IDADE_3\":\"")
                        .append(calculaIdade(alunos.get(i).getNascimento()))
                        .append("\",");
                alunosJSON.append("\"SEXO_3\":\"")
                        .append(alunos.get(i).getSexo())
                        .append("\",");
                alunosJSON.append("\"CALCADO_3\":")
                        .append(StringUtils.isEmpty(alunos.get(i).getSapato()) ? "" : alunos.get(i).getSapato())
                        .append(",");
                alunosJSON.append("\"BLUSA_3\":\"")
                        .append(StringUtils.isEmpty(alunos.get(i).getCamisa()) ? "" : alunos.get(i).getCamisa())
                        .append("\",");
                alunosJSON.append("\"CALCA_3\":\"")
                        .append(StringUtils.isEmpty(alunos.get(i).getCalca()) ? "" : alunos.get(i).getCalca())
                        .append("\"");
            } else {

                alunosJSON.append(",\"DOMINGO_3\":\"\",");
                alunosJSON.append("\"SACOLINHA_3\":\"\",");
                alunosJSON.append("\"NOME_3\":\"\",");
                alunosJSON.append("\"SERIE_3\":\"\",");
                alunosJSON.append("\"SALA_3\":\"\",");
                alunosJSON.append("\"CRACHA_3\":\"\",");
                alunosJSON.append("\"IDADE_3\":\"\",");
                alunosJSON.append("\"SEXO_3\":\"\",");
                alunosJSON.append("\"CALCADO_3\":\"\",");
                alunosJSON.append("\"BLUSA_3\":\"\",");
                alunosJSON.append("\"CALCA_3\":\"\"");
            }

            alunosJSON.append("}");
        }

        alunosJSON.append("]");

        File file = ResourceUtils.getFile("classpath:FichaSacolinha.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = new HashMap<>();

        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(alunosJSON.toString().getBytes());
        JsonDataSource ds = new JsonDataSource(jsonDataStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] preencherJasperLista(String domingo, String serie, String sala, Boolean ativos) throws JRException, FileNotFoundException {
        StringBuilder alunosJSON = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Aluno> alunos;

        if (!StringUtils.isEmpty(domingo)) {
            alunos = ativos ? alunoRepository.getAlunosAtivosPorDomingo(domingo) : alunoRepository.getAlunosPorDomingo(domingo);
        } else if (!StringUtils.isEmpty(serie)) {
            alunos = ativos ? alunoRepository.getAlunosAtivosPorSerie(serie) : alunoRepository.getAlunosPorSerie(serie);
        } else if (!StringUtils.isEmpty(sala)) {
            alunos = ativos ? alunoRepository.getAlunosAtivosPorSala(sala) : alunoRepository.getAlunosPorSala(sala);
        } else {
            alunos = ativos ? alunoRepository.getAlunosAtivos() : alunoRepository.findAll();
        }

        Map<String, List<Aluno>> alunosPorSerie = new TreeMap<>();

        alunos.forEach(aluno -> {
            String key = aluno.getSerie().getSerie();
            if (alunosPorSerie.containsKey(key)) {
                alunosPorSerie.get(key).add(aluno);
            } else {
                alunosPorSerie.put(key, new ArrayList<>());
                alunosPorSerie.get(key).add(aluno);
            }
        });

        alunosJSON.append("[");
        for (String key : alunosPorSerie.keySet()) {
            List<Aluno> alunosDaSerie = alunosPorSerie.get(key);
            int pagina = 1;

            for (int i = 0; i < alunosDaSerie.size(); i++) {
                int contador = 0;
                alunosJSON.append(alunosJSON.toString().equals("[") ? "{" : ",{");
                alunosJSON.append("\"DOMINGO\":\"DOM-")
                        .append(alunosDaSerie.get(0).getSerie().getDomingo())
                        .append("\",");
                alunosJSON.append("\"MES_E_ANO\":\"")
                        .append(calcularMesEAno())
                        .append("\",");
                alunosJSON.append("\"SERIE\":\"")
                        .append(key)
                        .append("\",");
                alunosJSON.append("\"SALA\":\"")
                        .append(alunosDaSerie.get(0).getSerie().getSala().split(" ")[1])
                        .append("\",");
                alunosJSON.append("\"PAGINA\":\"")
                        .append(pagina)
                        .append("\",");
                alunosJSON.append("\"TOTAL_ALUNOS\":\"")
                        .append(alunosDaSerie.size())
                        .append(" ALUNOS\",");

                alunosJSON.append("\"CODIGO_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getCodigo())
                        .append("\",");
                alunosJSON.append("\"NOME_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getNome())
                        .append("\",");
                alunosJSON.append("\"SEXO_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getSexo())
                        .append("\",");
                alunosJSON.append("\"NASCIMENTO_1\": \"")
                        .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                        .append("\",");
                alunosJSON.append("\"TENIS_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getSapato())
                        .append("\",");
                alunosJSON.append("\"BLUSA_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getCamisa())
                        .append("\",");
                alunosJSON.append("\"CALCA_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getCalca())
                        .append("\",");
                alunosJSON.append("\"FEV_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                        .append("\",");
                alunosJSON.append("\"MAR_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                        .append("\",");
                alunosJSON.append("\"ABR_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                        .append("\",");
                alunosJSON.append("\"MAI_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                        .append("\",");
                alunosJSON.append("\"JUN_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                        .append("\",");
                alunosJSON.append("\"AGO_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                        .append("\",");
                alunosJSON.append("\"SET_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                        .append("\",");
                alunosJSON.append("\"OUT_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                        .append("\",");
                alunosJSON.append("\"NOV_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                        .append("\",");
                alunosJSON.append("\"SAIR_SO_1\":\"")
                        .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                        .append("\",");

                contador++;

                if ((i + 1) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_2\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_2\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\",");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_2\":\"\",");
                    alunosJSON.append("\"NOME_2\":\"\",");
                    alunosJSON.append("\"SEXO_2\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_2\": \"\",");
                    alunosJSON.append("\"TENIS_2\":\"\",");
                    alunosJSON.append("\"BLUSA_2\":\"\",");
                    alunosJSON.append("\"CALCA_2\":\"\",");
                    alunosJSON.append("\"FEV_2\":\"\",");
                    alunosJSON.append("\"MAR_2\":\"\",");
                    alunosJSON.append("\"ABR_2\":\"\",");
                    alunosJSON.append("\"MAI_2\":\"\",");
                    alunosJSON.append("\"JUN_2\":\"\",");
                    alunosJSON.append("\"AGO_2\":\"\",");
                    alunosJSON.append("\"SET_2\":\"\",");
                    alunosJSON.append("\"OUT_2\":\"\",");
                    alunosJSON.append("\"NOV_2\":\"\",");
                    alunosJSON.append("\"SAIR_SO_2\":\"\",");
                    contador++;
                }

                if ((i + 2) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_3\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_3\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\",");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_3\":\"\",");
                    alunosJSON.append("\"NOME_3\":\"\",");
                    alunosJSON.append("\"SEXO_3\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_3\": \"\",");
                    alunosJSON.append("\"TENIS_3\":\"\",");
                    alunosJSON.append("\"BLUSA_3\":\"\",");
                    alunosJSON.append("\"CALCA_3\":\"\",");
                    alunosJSON.append("\"FEV_3\":\"\",");
                    alunosJSON.append("\"MAR_3\":\"\",");
                    alunosJSON.append("\"ABR_3\":\"\",");
                    alunosJSON.append("\"MAI_3\":\"\",");
                    alunosJSON.append("\"JUN_3\":\"\",");
                    alunosJSON.append("\"AGO_3\":\"\",");
                    alunosJSON.append("\"SET_3\":\"\",");
                    alunosJSON.append("\"OUT_3\":\"\",");
                    alunosJSON.append("\"NOV_3\":\"\",");
                    alunosJSON.append("\"SAIR_SO_3\":\"\",");
                    contador++;

                }

                if ((i + 3) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_4\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_4\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\",");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_4\":\"\",");
                    alunosJSON.append("\"NOME_4\":\"\",");
                    alunosJSON.append("\"SEXO_4\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_4\": \"\",");
                    alunosJSON.append("\"TENIS_4\":\"\",");
                    alunosJSON.append("\"BLUSA_4\":\"\",");
                    alunosJSON.append("\"CALCA_4\":\"\",");
                    alunosJSON.append("\"FEV_4\":\"\",");
                    alunosJSON.append("\"MAR_4\":\"\",");
                    alunosJSON.append("\"ABR_4\":\"\",");
                    alunosJSON.append("\"MAI_4\":\"\",");
                    alunosJSON.append("\"JUN_4\":\"\",");
                    alunosJSON.append("\"AGO_4\":\"\",");
                    alunosJSON.append("\"SET_4\":\"\",");
                    alunosJSON.append("\"OUT_4\":\"\",");
                    alunosJSON.append("\"NOV_4\":\"\",");
                    alunosJSON.append("\"SAIR_SO_4\":\"\",");
                    contador++;

                }

                if ((i + 4) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_5\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_5\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\",");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_5\":\"\",");
                    alunosJSON.append("\"NOME_5\":\"\",");
                    alunosJSON.append("\"SEXO_5\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_5\": \"\",");
                    alunosJSON.append("\"TENIS_5\":\"\",");
                    alunosJSON.append("\"BLUSA_5\":\"\",");
                    alunosJSON.append("\"CALCA_5\":\"\",");
                    alunosJSON.append("\"FEV_5\":\"\",");
                    alunosJSON.append("\"MAR_5\":\"\",");
                    alunosJSON.append("\"ABR_5\":\"\",");
                    alunosJSON.append("\"MAI_5\":\"\",");
                    alunosJSON.append("\"JUN_5\":\"\",");
                    alunosJSON.append("\"AGO_5\":\"\",");
                    alunosJSON.append("\"SET_5\":\"\",");
                    alunosJSON.append("\"OUT_5\":\"\",");
                    alunosJSON.append("\"NOV_5\":\"\",");
                    alunosJSON.append("\"SAIR_SO_5\":\"\",");
                    contador++;

                }

                if ((i + 5) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_6\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_6\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\",");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_6\":\"\",");
                    alunosJSON.append("\"NOME_6\":\"\",");
                    alunosJSON.append("\"SEXO_6\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_6\": \"\",");
                    alunosJSON.append("\"TENIS_6\":\"\",");
                    alunosJSON.append("\"BLUSA_6\":\"\",");
                    alunosJSON.append("\"CALCA_6\":\"\",");
                    alunosJSON.append("\"FEV_6\":\"\",");
                    alunosJSON.append("\"MAR_6\":\"\",");
                    alunosJSON.append("\"ABR_6\":\"\",");
                    alunosJSON.append("\"MAI_6\":\"\",");
                    alunosJSON.append("\"JUN_6\":\"\",");
                    alunosJSON.append("\"AGO_6\":\"\",");
                    alunosJSON.append("\"SET_6\":\"\",");
                    alunosJSON.append("\"OUT_6\":\"\",");
                    alunosJSON.append("\"NOV_6\":\"\",");
                    alunosJSON.append("\"SAIR_SO_6\":\"\",");
                    contador++;

                }

                if ((i + 6) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_7\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_7\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\",");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_7\":\"\",");
                    alunosJSON.append("\"NOME_7\":\"\",");
                    alunosJSON.append("\"SEXO_7\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_7\": \"\",");
                    alunosJSON.append("\"TENIS_7\":\"\",");
                    alunosJSON.append("\"BLUSA_7\":\"\",");
                    alunosJSON.append("\"CALCA_7\":\"\",");
                    alunosJSON.append("\"FEV_7\":\"\",");
                    alunosJSON.append("\"MAR_7\":\"\",");
                    alunosJSON.append("\"ABR_7\":\"\",");
                    alunosJSON.append("\"MAI_7\":\"\",");
                    alunosJSON.append("\"JUN_7\":\"\",");
                    alunosJSON.append("\"AGO_7\":\"\",");
                    alunosJSON.append("\"SET_7\":\"\",");
                    alunosJSON.append("\"OUT_7\":\"\",");
                    alunosJSON.append("\"NOV_7\":\"\",");
                    alunosJSON.append("\"SAIR_SO_7\":\"\",");
                    contador++;

                }

                if ((i + 7) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_8\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_8\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\",");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_8\":\"\",");
                    alunosJSON.append("\"NOME_8\":\"\",");
                    alunosJSON.append("\"SEXO_8\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_8\": \"\",");
                    alunosJSON.append("\"TENIS_8\":\"\",");
                    alunosJSON.append("\"BLUSA_8\":\"\",");
                    alunosJSON.append("\"CALCA_8\":\"\",");
                    alunosJSON.append("\"FEV_8\":\"\",");
                    alunosJSON.append("\"MAR_8\":\"\",");
                    alunosJSON.append("\"ABR_8\":\"\",");
                    alunosJSON.append("\"MAI_8\":\"\",");
                    alunosJSON.append("\"JUN_8\":\"\",");
                    alunosJSON.append("\"AGO_8\":\"\",");
                    alunosJSON.append("\"SET_8\":\"\",");
                    alunosJSON.append("\"OUT_8\":\"\",");
                    alunosJSON.append("\"NOV_8\":\"\",");
                    alunosJSON.append("\"SAIR_SO_8\":\"\",");
                    contador++;

                }

                if ((i + 8) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_9\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_9\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\",");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_9\":\"\",");
                    alunosJSON.append("\"NOME_9\":\"\",");
                    alunosJSON.append("\"SEXO_9\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_9\": \"\",");
                    alunosJSON.append("\"TENIS_9\":\"\",");
                    alunosJSON.append("\"BLUSA_9\":\"\",");
                    alunosJSON.append("\"CALCA_9\":\"\",");
                    alunosJSON.append("\"FEV_9\":\"\",");
                    alunosJSON.append("\"MAR_9\":\"\",");
                    alunosJSON.append("\"ABR_9\":\"\",");
                    alunosJSON.append("\"MAI_9\":\"\",");
                    alunosJSON.append("\"JUN_9\":\"\",");
                    alunosJSON.append("\"AGO_9\":\"\",");
                    alunosJSON.append("\"SET_9\":\"\",");
                    alunosJSON.append("\"OUT_9\":\"\",");
                    alunosJSON.append("\"NOV_9\":\"\",");
                    alunosJSON.append("\"SAIR_SO_9\":\"\",");
                    contador++;

                }

                if ((i + 9) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_10\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_10\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\",");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_10\":\"\",");
                    alunosJSON.append("\"NOME_10\":\"\",");
                    alunosJSON.append("\"SEXO_10\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_10\": \"\",");
                    alunosJSON.append("\"TENIS_10\":\"\",");
                    alunosJSON.append("\"BLUSA_10\":\"\",");
                    alunosJSON.append("\"CALCA_10\":\"\",");
                    alunosJSON.append("\"FEV_10\":\"\",");
                    alunosJSON.append("\"MAR_10\":\"\",");
                    alunosJSON.append("\"ABR_10\":\"\",");
                    alunosJSON.append("\"MAI_10\":\"\",");
                    alunosJSON.append("\"JUN_10\":\"\",");
                    alunosJSON.append("\"AGO_10\":\"\",");
                    alunosJSON.append("\"SET_10\":\"\",");
                    alunosJSON.append("\"OUT_10\":\"\",");
                    alunosJSON.append("\"NOV_10\":\"\",");
                    alunosJSON.append("\"SAIR_SO_10\":\"\",");
                    contador++;

                }

                if ((i + 10) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_11\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_11\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\",");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_11\":\"\",");
                    alunosJSON.append("\"NOME_11\":\"\",");
                    alunosJSON.append("\"SEXO_11\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_11\": \"\",");
                    alunosJSON.append("\"TENIS_11\":\"\",");
                    alunosJSON.append("\"BLUSA_11\":\"\",");
                    alunosJSON.append("\"CALCA_11\":\"\",");
                    alunosJSON.append("\"FEV_11\":\"\",");
                    alunosJSON.append("\"MAR_11\":\"\",");
                    alunosJSON.append("\"ABR_11\":\"\",");
                    alunosJSON.append("\"MAI_11\":\"\",");
                    alunosJSON.append("\"JUN_11\":\"\",");
                    alunosJSON.append("\"AGO_11\":\"\",");
                    alunosJSON.append("\"SET_11\":\"\",");
                    alunosJSON.append("\"OUT_11\":\"\",");
                    alunosJSON.append("\"NOV_11\":\"\",");
                    alunosJSON.append("\"SAIR_SO_11\":\"\",");
                    contador++;

                }

                if ((i + 11) < alunosDaSerie.size()) {
                    alunosJSON.append("\"CODIGO_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getCodigo())
                            .append("\",");
                    alunosJSON.append("\"NOME_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getNome())
                            .append("\",");
                    alunosJSON.append("\"SEXO_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getSexo())
                            .append("\",");
                    alunosJSON.append("\"NASCIMENTO_12\": \"")
                            .append(sdf.format(alunosDaSerie.get(i + contador).getNascimento()))
                            .append("\",");
                    alunosJSON.append("\"TENIS_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getSapato())
                            .append("\",");
                    alunosJSON.append("\"BLUSA_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getCamisa())
                            .append("\",");
                    alunosJSON.append("\"CALCA_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getCalca())
                            .append("\",");
                    alunosJSON.append("\"FEV_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getFevereiro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAR_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMarco().getSigla())
                            .append("\",");
                    alunosJSON.append("\"ABR_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAbril().getSigla())
                            .append("\",");
                    alunosJSON.append("\"MAI_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getMaio().getSigla())
                            .append("\",");
                    alunosJSON.append("\"JUN_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getJunho().getSigla())
                            .append("\",");
                    alunosJSON.append("\"AGO_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getAgosto().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SET_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getSetembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"OUT_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getOutubro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"NOV_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getRegistroPresencas().getNovembro().getSigla())
                            .append("\",");
                    alunosJSON.append("\"SAIR_SO_12\":\"")
                            .append(alunosDaSerie.get(i + contador).getSairSozinho() ? "SIM" : "NÃO")
                            .append("\"");
                    contador++;
                } else {
                    alunosJSON.append("\"CODIGO_12\":\"\",");
                    alunosJSON.append("\"NOME_12\":\"\",");
                    alunosJSON.append("\"SEXO_12\":\"\",");
                    alunosJSON.append("\"NASCIMENTO_12\": \"\",");
                    alunosJSON.append("\"TENIS_12\":\"\",");
                    alunosJSON.append("\"BLUSA_12\":\"\",");
                    alunosJSON.append("\"CALCA_12\":\"\",");
                    alunosJSON.append("\"FEV_12\":\"\",");
                    alunosJSON.append("\"MAR_12\":\"\",");
                    alunosJSON.append("\"ABR_12\":\"\",");
                    alunosJSON.append("\"MAI_12\":\"\",");
                    alunosJSON.append("\"JUN_12\":\"\",");
                    alunosJSON.append("\"AGO_12\":\"\",");
                    alunosJSON.append("\"SET_12\":\"\",");
                    alunosJSON.append("\"OUT_12\":\"\",");
                    alunosJSON.append("\"NOV_12\":\"\",");
                    alunosJSON.append("\"SAIR_SO_12\":\"\"");
                    contador++;
                }

                i += (contador - 1);
                pagina++;

                alunosJSON.append("}");
            }
        }

        alunosJSON.append("]");

        File file = ResourceUtils.getFile("classpath:ListaDeSalas.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = new HashMap<>();

        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(alunosJSON.toString().getBytes());
        JsonDataSource ds = new JsonDataSource(jsonDataStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private String calculaIdade(Date nascimento) {
        Calendar hoje = Calendar.getInstance();
        hoje.setTime(new Date());

        Calendar nascimentoCalendar = Calendar.getInstance();
        nascimentoCalendar.setTime(nascimento);

        return String.valueOf(hoje.get(Calendar.YEAR) - nascimentoCalendar.get(Calendar.YEAR)) + " anos";
    }

    private String calcularMesEAno() {
        SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
        SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");

        switch (sdfMes.format(new Date())) {
            case "01":
            case "02":
                return "FEVEREIRO/" + sdfAno.format(new Date());
            case "03":
                return "MARÇO/" + sdfAno.format(new Date());
            case "04":
                return "ABRIL" + sdfAno.format(new Date());
            case "05":
                return "MAIO/" + sdfAno.format(new Date());
            case "06":
                return "JUNHO/" + sdfAno.format(new Date());
            case "07":
            case "08":
                return "AGOSTO/" + sdfAno.format(new Date());
            case "09":
                return "SETEMBRO/" + sdfAno.format(new Date());
            case "10":
                return "OUTUBRO/" + sdfAno.format(new Date());
            default:
                return "NOVEMBRO/" + sdfAno.format(new Date());
        }
    }
}
