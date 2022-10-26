package cdle.wordcount.mr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import cdle.wordcount.mr.formaters.input.MyFileInputFormat;
import cdle.wordcount.mr.formaters.input.MyLogUtils;
import cdle.wordcount.mr.formaters.input.MyTextInputFormat;

public class WordCountApplication extends Configured implements Tool {


	private static Log log;

	static {
		Class<?> klass;
		klass = WordCountApplication.class;

		log = LogFactory.getLog( klass );
		MyLogUtils.showDebugLevel( log, klass );
	}
	
	public static void main(String[] args) throws Exception {
		System.exit( ToolRunner.run( new WordCountApplication(), args) );
	}

	@Override
	public int run(String[] args) throws Exception {
		if ( args.length<2 ) {
			System.err.println( "hadoop ... <input path> <output path> [number of reducers]" );
			System.exit(-1);
		}

		Job job = Job.getInstance( getConf() );

		job.setJarByClass( WordCountApplication.class );
		job.setJobName( "Word Count Ver 1" );

		FileInputFormat.addInputPath(job, new Path(args[0]) );
		FileOutputFormat.setOutputPath(job, new Path(args[1]) );

		job.setMapperClass( WordCountMapper.class );
		job.setCombinerClass( WordCountReducer.class );
		job.setReducerClass( WordCountReducer.class );

		// Set input path
		MyFileInputFormat.addInputPath(job, new Path(args[0]) );

		// Set input class format
		// Can be done using properties
		// -D mapreduce.job.inputformat.class=demo.formaters.input.MyTextInputFormat
		job.setInputFormatClass( MyTextInputFormat.class );

		// Output types of reduce function
		job.setOutputKeyClass( Text.class );
		job.setOutputValueClass( IntWritable.class );

		// Set output path
		FileOutputFormat.setOutputPath(job, new Path(args[1]) );

		boolean retCode = job.waitForCompletion( true );

		if ( retCode==true ) {
			Counter distinctsCounter = job.getCounters().findCounter( WordCountUtils.Statistics.Distincts );
			Counter singletonsCounter = job.getCounters().findCounter( WordCountUtils.Statistics.Singletons );
			Counter totalCounter = job.getCounters().findCounter( WordCountUtils.Statistics.TotalWords );

			System.out.printf( "Number of distinct n-grams: %d\n", distinctsCounter.getValue() );
			System.out.printf( "Number of singletons n-grams: %d\n", singletonsCounter.getValue() );
			System.out.printf( "Number of total n-grams: %d\n", totalCounter.getValue() );
		}
		else {
			System.out.printf( "Job failed!\n" );
		}


		return retCode ? 0 : 1;
	}
}
