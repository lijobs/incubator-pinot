package com.linkedin.pinot.controller.api.reslet.resources.v2;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.helix.ZNRecord;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkedin.pinot.common.config.AbstractTableConfig;
import com.linkedin.pinot.controller.ControllerConf;
import com.linkedin.pinot.controller.helix.core.PinotHelixResourceManager;


public class PinotTableRestletResource extends ServerResource {
  private static final Logger LOGGER = LoggerFactory.getLogger(PinotTableRestletResource.class);
  private final ControllerConf conf;
  private final PinotHelixResourceManager manager;
  private final File baseDataDir;
  private final File tempDir;

  public PinotTableRestletResource() throws IOException {
    conf = (ControllerConf) getApplication().getContext().getAttributes().get(ControllerConf.class.toString());
    manager =
        (PinotHelixResourceManager) getApplication().getContext().getAttributes()
            .get(PinotHelixResourceManager.class.toString());
    baseDataDir = new File(conf.getDataDir());
    if (!baseDataDir.exists()) {
      FileUtils.forceMkdir(baseDataDir);
    }
    tempDir = new File(baseDataDir, "schemasTemp");
    if (!tempDir.exists()) {
      FileUtils.forceMkdir(tempDir);
    }
  }

  @Override
  @Post("json")
  public Representation post(Representation entity) {
    StringRepresentation presentation = null;
    AbstractTableConfig config = null;
    try {
      String jsonRequest = entity.getText();
      config = AbstractTableConfig.init(jsonRequest);
      System.out.println("original : " + config);
      ZNRecord rec = AbstractTableConfig.toZnRecord(config);
      System.out.println("from znRecord : " + AbstractTableConfig.fromZnRecord(rec));
      return new StringRepresentation(config.toString());
    } catch (Exception e) {
      LOGGER.error("error reading/serializing requestJSON", e);
    }

    try {

    } catch (Exception e) {

    }
    return null;
  }

  @Override
  @Get
  public Representation get() {
    StringRepresentation presentation = null;

    final String tableName = (String) getRequest().getAttributes().get("tableName");
    if (tableName == null) {
      return new StringRepresentation("tableName is not present");
    }

    // fetch all table configs
    return presentation;
  }
}
