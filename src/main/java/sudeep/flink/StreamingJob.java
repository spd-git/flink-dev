/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sudeep.flink;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

	private SourceFunction<Long> source;
	private SinkFunction<Long> sink;
	private final boolean SHOW_ON_UI = false;

	public StreamingJob(SourceFunction<Long> source, SinkFunction<Long> sink) {
		this.source = source;
		this.sink = sink;
	}

	public JobExecutionResult execute() throws Exception {
		// set up the streaming execution environment
		StreamExecutionEnvironment env;
		if (SHOW_ON_UI) {
			Configuration conf = new Configuration();
			env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(conf);
		} else {
			env = StreamExecutionEnvironment.getExecutionEnvironment();
		}


		/*
		 * Here, you can start creating your execution plan for Flink.
		 *
		 * Start with getting some data from the environment, like
		 * 	env.readTextFile(textPath);
		 *
		 * then, transform the resulting DataStream<String> using operations
		 * like
		 * 	.filter()
		 * 	.flatMap()
		 * 	.join()
		 * 	.coGroup()
		 *
		 * and many more.
		 * Have a look at the programming guide for the Java API:
		 *
		 * https://flink.apache.org/docs/latest/apis/streaming/index.html
		 *
		 */

		DataStream<Long> stream =
				env.addSource(source)
						.returns(TypeInformation.of(Long.class));

		stream.map(new IncrementMapFunction())
				.addSink(sink);

		// execute program
		return env.execute("Flink Streaming Java API Skeleton");
	}

	public static void main(String[] args) throws Exception {
		StreamingJob job = new StreamingJob(new RandomLongSource(), new PrintSinkFunction<>());
		job.execute();
	}

	public class IncrementMapFunction implements MapFunction<Long, Long> {

		@Override
		public Long map(Long record) throws Exception {
			return record + 1;
		}
	}
}
