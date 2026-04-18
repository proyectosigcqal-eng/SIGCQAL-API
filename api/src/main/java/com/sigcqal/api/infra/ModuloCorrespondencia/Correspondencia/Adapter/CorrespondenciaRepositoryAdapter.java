package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Adapter;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Model.Correspondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Port.CorrespondenciaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Mapper.CorrespondenciaEntradaMapper;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Repository.CorrespondenciaEntradaJpaRepository;

@Component
public class CorrespondenciaRepositoryAdapter implements CorrespondenciaRepositoryPort {
    private static final Pattern FOLIO_PATTERN = Pattern.compile("^CEDECON-CORR-(\\d+)/(\\d{4})$");

    private final CorrespondenciaEntradaJpaRepository jpaRepository;
    private final CorrespondenciaEntradaMapper mapper;

    public CorrespondenciaRepositoryAdapter(CorrespondenciaEntradaJpaRepository jpaRepository, CorrespondenciaEntradaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Correspondencia save(Correspondencia correspondencia) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(correspondencia)));
    }

    @Override
    public Optional<Correspondencia> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsByNumeroOficio(String numeroOficio) {
        return jpaRepository.existsByNumeroOficio(numeroOficio);
    }

    @Override
    public Optional<Long> findLastConsecutivoByAnio(Integer anio) {
        if (anio == null || anio <= 0) return Optional.empty();

        return jpaRepository
                .findTopByFolioUnicoEndingWithOrderByIdDesc("/" + anio)
                .flatMap(e -> parseConsecutivo(e.getFolioUnico(), anio));
    }

    private Optional<Long> parseConsecutivo(String folioUnico, Integer anio) {
        if (folioUnico == null || anio == null) return Optional.empty();

        Matcher matcher = FOLIO_PATTERN.matcher(folioUnico);
        if (!matcher.matches()) return Optional.empty();

        Long consecutivo = Long.valueOf(matcher.group(1));
        Integer folioAnio = Integer.valueOf(matcher.group(2));
        if (!anio.equals(folioAnio)) return Optional.empty();

        return Optional.of(consecutivo);
    }
}
