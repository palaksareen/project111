package uk.co.o2.kibana;


import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.PickledGraphite;

import uk.co.o2.DynamicProperties;

@Component
public class GrafanaReporter {

    private PickledGraphite graphite;
    private GraphiteReporter graphiteReporter;
    private MetricRegistry metricRegistry=new MetricRegistry();
    
    private final Logger logger = Logger.getLogger(getClass());
    private final String hostName = DynamicProperties.getProperty("kairos.db.hostname");
    private final int port = Integer.parseInt(DynamicProperties.getProperty("kairos.db.port"));
    private final String prefix = DynamicProperties.getProperty("kairos.db.prefix");
    private int period = Integer.parseInt(DynamicProperties.getProperty("kairos.db.period"));
    
    //@Autowired
    public GrafanaReporter() {
        
        graphite = new PickledGraphite(new InetSocketAddress(hostName, port));

        logger.info("Graphite initialised");
    }

    
    @PostConstruct
    public void sendStatsToGraphite() {
    	
        logger.info("Graphite Reporter initialising");
        graphiteReporter = GraphiteReporter.forRegistry(metricRegistry)
                .prefixedWith(prefix)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);

        graphiteReporter.start(period, TimeUnit.MINUTES);
        graphiteReporter.report(metricRegistry.getGauges(), metricRegistry.getCounters(), metricRegistry.getHistograms(), metricRegistry.getMeters(), metricRegistry.getTimers());
        logger.info("Graphite started");
    }

}