package hr.ja.bio.parser.old;

import hr.ja.bio.model.Project;
import hr.ja.bio.parser.model.PathwayResult;
import hr.ja.bio.parser.model.SampleFileType;
import hr.ja.bio.parser.model.TaxonomyResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.StringReader;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class SampleFileParserAsync implements Callable<TaskResult> {

    private String status = "Not started";
    private String content;
    private Project project;
    private SampleFileType type;
    private String fileName;

    public SampleFileParserAsync(String content, Project project, String fileName, SampleFileType type) {
        this.content = content;
        this.project = project;
        this.fileName = fileName;
        this.type = type;
    }

    @Override
    public TaskResult call() throws Exception {
        try {
            TaskResult taskResult = new TaskResult();
            if (type == SampleFileType.TAXONOMY) {
                log.debug("START taxonomy");
                status = "Parse taxonomy";
                TaxonomyParser parser = new TaxonomyParser(IOUtils.readLines(new StringReader(content)));
                TaxonomyResult result;

//				insertOneTaxonomyResult(parser.parse());
                return taskResult;
            }

            if (type == SampleFileType.TAXONOMY_MERGED) {
                log.debug("taxonomy marged");
                TaxonomyMergedParser parser = new TaxonomyMergedParser(content);
                TaxonomyMergedResult result = parser.parse();
                List<TaxonomyResult> taxonomyParseResults = result.getTaxonomyParseResults();
                status = "Parsed " + taxonomyParseResults.size() + " samples";
                for (TaxonomyResult ta : taxonomyParseResults) {
//					insertOneTaxonomyResult(ta);
                }
                log.debug("finish taxonomy merged");
                return taskResult;
            }
            if (type == SampleFileType.PATHWAY) {
                PathwayParser parser = new PathwayParser();
                PathwayResult result = parser.parse(content);

            }

            throw new RuntimeException("unknow type " + type);

        } catch (Throwable e) {
            log.error("", e);
            throw e;
        }
    }

//	private void insertOneTaxonomyResult(TaxonomyResult result) throws IOException {
//		status = "parsed " + result.lineageAbundances.size() + " taxonomyes";
//
//		try (Handle handle = db.getJdbi().open()) {
//			SampleDao dao = handle.attach(SampleDao.class);
//			TaxonomyAbundanceDao taxDao = handle.attach(TaxonomyAbundanceDao.class);
//			Sample sample = dao.findSample(result.sampleName, project.getId());
//
//			if (sample == null) {
//				sample = new Sample();
//				sample.setName(result.sampleName);
//				sample.setProject(project);
//				dao.insertSample(sample);
//			} else {
//				log.debug("delete sve from samples {}", sample.getId());
//				taxDao.deleteAllTaxonomy(sample.getId());
//			}
//			for (TaxonomyAbundance ta : result.lineageAbundances) {
//				ta.setSample(sample);
//			}
//			db.bulkInsert(result.lineageAbundances);
//		}
//
//	}

    public String getStatus() {
        return status;
    }

    public String getUserTaskName() {
        return "Parse files";
    }

}