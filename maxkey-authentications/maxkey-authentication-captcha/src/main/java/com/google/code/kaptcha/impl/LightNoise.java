/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package com.google.code.kaptcha.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;

/**
 * The default implementation of {@link NoiseProducer}, adds a noise on an
 * image.
 */
public class LightNoise extends Configurable implements NoiseProducer
{
	/**
	 * Draws a noise on the image. The noise curve depends on the factor values.
	 * Noise won't be visible if all factors have the value > 1.0f
	 * 
	 * @param image
	 *            the image to add the noise to
	 * @param factorOne
	 * @param factorTwo
	 * @param factorThree
	 * @param factorFour
	 */
	@Override
	public void makeNoise(BufferedImage image, float factorOne,
			float factorTwo, float factorThree, float factorFour)
	{
		Color color = getConfig().getNoiseColor();

		// image size
		int width = image.getWidth();
		int height = image.getHeight();

		// the points where the line changes the stroke and direction
		Point2D[] pts = null;
		Random rand = new SecureRandom();

		// the curve from where the points are taken
		CubicCurve2D cc = new CubicCurve2D.Float(width * factorOne, height
				* rand.nextFloat(), width * factorTwo, height
				* rand.nextFloat(), width * factorThree, height
				* rand.nextFloat(), width * factorFour, height
				* rand.nextFloat());

		// creates an iterator to define the boundary of the flattened curve
		PathIterator pi = cc.getPathIterator(null, 2);
		Point2D tmp[] = new Point2D[200];
		int i = 0;

		// while pi is iterating the curve, adds points to tmp array
		while (!pi.isDone())
		{
			float[] coords = new float[6];
			switch (pi.currentSegment(coords)){
				case PathIterator.SEG_MOVETO,PathIterator.SEG_LINETO:{
					tmp[i] = new Point2D.Float(coords[0], coords[1]);
				}
			}
			i++;
			pi.next();
		}

		pts = new Point2D[i];
		System.arraycopy(tmp, 0, pts, 0, i);

		Graphics2D graph = (Graphics2D) image.getGraphics();
		graph.setRenderingHints(new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON));

		graph.setColor(color);

		// for the maximum 3 point change the stroke and direction
		for (i = 0; i < pts.length - 1; i++)
		{
			if (i < 3) {
				graph.setStroke(new BasicStroke(0.7f * (2 - i)));
			}
			graph.drawLine((int) pts[i].getX(), (int) pts[i].getY(),
					(int) pts[i + 1].getX(), (int) pts[i + 1].getY());
		}

		graph.dispose();
	}
}
