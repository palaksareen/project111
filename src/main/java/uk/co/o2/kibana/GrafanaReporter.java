package uk.co.o2.kibana;


import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.PickledGraphite;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;

import uk.co.o2.DynamicProperties;

@Component
@EnableMetrics
public class GrafanaReporter {

    private PickledGraphite graphite;
    private GraphiteReporter graphiteReporter;
    private MetricRegistry metricRegistry=new MetricRegistry();
    
    private final Log logger = LogFactory.getLog("application_log");
    private final String hostName = DynamicProperties.getProperty("kairosDbHostname");
    private final int port = Integer.parseInt(DynamicProperties.getProperty("kairosDbPort"));
    private final String prefix = DynamicProperties.getProperty("kairosDbPrefix");
    private int period = Integer.parseInt(DynamicProperties.getProperty("kairosDbPeriod"));
    
    @Autowired
    public GrafanaReporter(MetricRegistry metricRegistry) {
        
    	this.metricRegistry = metricRegistry;
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