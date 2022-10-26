package cdle.temperatures.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxTemperatureApplicationWithCompression {
	
	public static void main(String[] args) throws Exception {
		if ( args.length!=2 ) {
			System.err.println( "hadoop ... <input path> <output path>" );
			System.exit( -1 );
		}
		
		Job job = Job.getInstance( new Configuration() );
		
		job.setJarByClass( MaxTemperatureApplicationWithCompression.class );
		job.setJobName( "Max temperature" );
		
		FileInputFormat.addInputPath(job, new Path(args[0]) );
		FileOutputFormat.setOutputPath(job, new Path(args[1]) );
		
		job.setMapperClass( MaxTemperatureMapper.class );
		job.setReducerClass( MaxTemperatureReducer.class );
		
		// Output types of map function
		job.setMapOutputKeyClass( Text.class );
		job.setMapOutputValueClass( IntWritable.class );
		
		// Output types of reduce function
		job.setOutputKeyClass( Text.class );
		job.setOutputValueClass( FloatWritable.class );
		
		FileOutputFormat.setCompressOutput(job, true);
		FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
		
		System.exit( job.waitForCompletion(true) ? 0 : 1 );
	}
}