/* ******************************************************************************
 *
 *       Copyright 2008-2010 Hans Dijkema
 *
 *   JRichTextEditor is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as 
 *   published by the Free Software Foundation, either version 3 of 
 *   the License, or (at your option) any later version.
 *
 *   JRichTextEditor is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with JRichTextEditor.  If not, see <http://www.gnu.org/licenses/>.
 *   
 * ******************************************************************************/

package nl.dykema.jxmlnote.html;

import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class XMLNoteReplacer {
	private String _m;
	private String _r;
	private String _me;
	private String _re;
	private String _mnotAllowedInNesting;
	private String _mnotAllowedOutsideNesting;
	private boolean _d=false;
	private boolean _stopper=false;
	private Type    _type=Type.STANDARD;
	private Vector<XMLNoteReplacer> _replacers;
	private Repl    _repl;

	enum Type { STANDARD, NESTED, REPL };
	
	public interface Repl {
		String process(String in);
	}
	
	Pattern pattern(String re) {
		Pattern p;
		if (_d) {
			p=Pattern.compile(re,Pattern.DOTALL|Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE);
		} else {
			p=Pattern.compile(re,Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE);
		}
		return p;
	}
	
	Pattern pattern() {
		return pattern(_m);
	}
	
	String replace() { 
		return _r; 
	}
	
	public String p() { 
		return _m; 
	}
	
	public boolean stopper() {
		return _stopper;
	}
	
	public XMLNoteReplacer getEndTag(String match,Vector<XMLNoteReplacer> r) {
		Iterator<XMLNoteReplacer> g=r.iterator();
		while (g.hasNext()) {
			XMLNoteReplacer rr=g.next();
			Pattern p=rr.pattern();
			Matcher m=p.matcher(match);
			if (m.find()) {
				return rr;
			} 
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	public String recursiveReplace(StringBuffer sb,Matcher m,int depth) {
		Pattern pb=pattern(_m);
		Pattern pe=pattern(_me);
		Pattern pn=pattern(_mnotAllowedInNesting);
		Pattern pa=pattern(_mnotAllowedOutsideNesting); // allowed in nesting 
		String match,begin,end,notAllowedInside,notAllowedOutside;
		Stack<String> endBlocks=new Stack<String>();
		// Add stack with nested tag.
		// Make one match and (match,replacers,closers  ) combinations for individual nestings.
		while(m.find()) {
			match=m.group();
			begin=null;end=null;notAllowedInside=null;notAllowedOutside=null;
			
			if (pb.matcher(match).find()) {
				begin=match;
			} else if (pe.matcher(match).find()) {
				end=match;
			} else if (pa.matcher(match).find()) {
				notAllowedOutside=match;  // allowed inside before allowed outside.
			} else {
				notAllowedInside=match;
			}
				
			if (begin!=null) {
				XMLNoteReplacer q=getEndTag(match,_replacers);
				m.appendReplacement(sb, q._r);
				depth+=1;
				endBlocks.push(q._re);
			} else if (end!=null) {
				depth-=1;
				if (depth<0) {  //end tag outside nesting; skip. 
					depth=0;  
					m.appendReplacement(sb,"");
				} else {
					m.appendReplacement(sb,endBlocks.pop());
				}
			} else if (notAllowedInside!=null) {
				if (depth>0) {  // a not allowed tag inside a nesting!
					int i;
					String rrr="";
					for(i=0;i<depth;i++) {
						rrr+=endBlocks.pop();
					}
					m.appendReplacement(sb,rrr);
					depth=0;
				} else { // nothing wrong, outside nesting.
					m.appendReplacement(sb,notAllowedInside);
				}
			} else {
				if (depth>0) { // nothing wrong, inside nesting.
					m.appendReplacement(sb, notAllowedOutside);
				} else {
					// not allowed outside the nesting. means: we dispose of the tag. However,
					// this means we must make sure that we process al outer possibilities and
					// match those somehow. And we make sure that we are being nice to simple
					// faults.
					m.appendReplacement(sb, "");
				}
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	public String toString() {
		return "{"+_m+","+_me+","+_r+","+_re+","+((_replacers==null) ? "" : _replacers.toString())+"}";
	}
	
	public String recursiveReplace(String str) {
		String re="("+_m+")|("+_me+")|("+_mnotAllowedInNesting+")|("+_mnotAllowedOutsideNesting+")";
		Pattern p=pattern(re);
		Matcher m=p.matcher(str);
		StringBuffer sb=new StringBuffer();
		return recursiveReplace(sb,m,0);
	}
	
	public String doReplace(String str) {
		if (_type==Type.STANDARD) {
			Pattern p=pattern();
			if (stopper()) {
				stopper();
				//System.out.println(p());
			}
			Matcher m=p.matcher(str);
			StringBuffer sb=new StringBuffer();
			while(m.find()) {
				m.appendReplacement(sb,replace());
			}
			m.appendTail(sb);
			return sb.toString();
		} else if (_type==Type.REPL) {
			return _repl.process(str);
		} else {
			return recursiveReplace(str);
		}
	}
	
	public XMLNoteReplacer(Repl r) {
		_type=Type.REPL;
		_repl=r;
	}
	
	public XMLNoteReplacer(String mb,String me, String mnot, String mnotout, Vector<XMLNoteReplacer> replacers,boolean dotall,boolean stopper) {
		_m=mb;
		_me=me;
		_mnotAllowedInNesting=mnot;
		_mnotAllowedOutsideNesting=mnotout;
		_r=null;
		_re=null;
		_replacers=replacers;
		_d=dotall;
		_stopper=stopper;
		_type=Type.NESTED;
	}

	public XMLNoteReplacer(String mb,String me, String mnot, String mnotout,Vector<XMLNoteReplacer> replacers,boolean dotall) {
		_m=mb;
		_me=me;
		_mnotAllowedInNesting=mnot;
		_mnotAllowedOutsideNesting=mnotout;
		_r=null;
		_re=null;
		_replacers=replacers;
		_d=dotall;
		_type=Type.NESTED;
	}
	
	public XMLNoteReplacer(String mb,String me,String rb,String re,boolean dotall) {
		_m=mb;
		_me=me;
		_r=rb;
		_re=re;
		_d=dotall;
		_type=Type.REPL;
	}
	
	public XMLNoteReplacer(String m,String repl,boolean dotall,boolean stopper) {
		_m=m;_r=repl;
		_d=dotall;
		_stopper=stopper;
	}
	
	public XMLNoteReplacer(String m,String repl,boolean dotall) {
		_m=m;_r=repl;
		_d=dotall;
	}

	public XMLNoteReplacer(String m,String repl) {
		_m=m;_r=repl;
	}
}


public class HtmlStripper {

	static HtmlStripper _stripper=null;
	
	private String correctNested(String in,String open,String close) {
		String regex="("+open+"|"+close+")";
		String[] parts=in.split(regex);
		return in;
	}
	
	private Vector<XMLNoteReplacer> getReplacers() {
		Vector<XMLNoteReplacer> r=new Vector<XMLNoteReplacer>();
		r.add(new XMLNoteReplacer("[<]head[^>]*[>].*?[<][/]head[>]","",true));
		r.add(new XMLNoteReplacer("[<]p[>](.*?)[<][/]p[>]","::P:PAR:P::$1::P:EPAR:P::",true));
		r.add(new XMLNoteReplacer("[<]p\\s[^>]+[>](.*?)[<][/]p[>]","::P:PAR:P::$1::P:EPAR:P::",true));
		r.add(new XMLNoteReplacer("[<]br[^>]*[>]","::E:BR:E::",true));
		
		r.add(new XMLNoteReplacer("[<]li[>]::P:PAR:P::(.*?)::P:EPAR:P::","<li>$1</li>",true));   // Correct OpenOffice behaviour.

		{
			Vector<XMLNoteReplacer> ulr=new Vector<XMLNoteReplacer>();
			ulr.add(new XMLNoteReplacer("[<]ul(\\s[^>]+){0,1}[>]","[<][/][uo]l[>]","::P:UL:P::","::P:EUL:P::",true));
			ulr.add(new XMLNoteReplacer("[<]ol(\\s[^>]+){0,1}[>]","[<][/][uo]l[>]","::P:OL:P::","::P:EOL:P::",true));
			ulr.add(new XMLNoteReplacer("[<]li(\\s[^>]+){0,1}[>]","[<][/]li[>]","::P:LI:P::","::P:ELI:P::",true));
			r.add(new XMLNoteReplacer("[<](ul|ol|li)(\\s[^>]+){0,1}[>]","[<][/](ul|ol|li)[>]",
					"::P:(PAR|EPAR):P::","([<][/]?li(\\s[^>]+){0,1}[>])",ulr,true,true
					)
			); 
		}
		
		r.add(new XMLNoteReplacer("[&]nbsp[;]"," ",true));

		//r.add(new XMLNoteReplacer("[<]li[>](.*?)[<][/]li[>]","::P:LI:P::$1::P:ELI:P::",true));
		//r.add(new XMLNoteReplacer("[<]li\\s[^>]+[>](.*?)[<][/]li[>]","::P:LI:P::$1::P:ELI:P::",true));
		
		r.add(new XMLNoteReplacer("[<]b(\\sclass=[\"]h[0-9][\"])\\s*[>](.*?)[<][/]b[>]","::EL:B:$1:EL::$2::EL:EB:EL::",true));
		r.add(new XMLNoteReplacer("[<]b[>](.*?)[<][/]b[>]","::EL:B:EL::$1::EL:EB:EL::",true));
		r.add(new XMLNoteReplacer("[<]b\\s[^>]+[>](.*?)[<][/]b[>]","::EL:B:EL::$1::EL:EB:EL::",true));
		r.add(new XMLNoteReplacer("[<]i[>](.*?)[<][/]i[>]","::EL:I:EL::$1::EL:EI:EL::",true));
		r.add(new XMLNoteReplacer("[<]i\\s[^>]+[>](.*?)[<][/]i[>]","::EL:I:EL::$1::EL:EI:EL::",true));
		r.add(new XMLNoteReplacer("[<]u[>](.*?)[<][/]u[>]","::EL:U:EL::$1::EL:EU:EL::",true));
		r.add(new XMLNoteReplacer("[<]u\\s[^>]+[>](.*?)[<][/]u[>]","::EL:U:EL::$1::EL:EU:EL::",true));
		
		r.add(new XMLNoteReplacer("[<]h1[>](.*?)[<][/]h1[>]","::EL:H1:EL::$1::EL:EH1:EL::",true));
		r.add(new XMLNoteReplacer("[<]h1\\s[^>]+[>](.*?)[<][/]h1[>]","::EL:H1:EL::$1::EL:EH1:EL::",true));
		r.add(new XMLNoteReplacer("[<]h2[>](.*?)[<][/]h2[>]","::EL:H2:EL::$1::EL:EH2:EL::",true));
		r.add(new XMLNoteReplacer("[<]h2\\s[^>]+[>](.*?)[<][/]h2[>]","::EL:H2:EL::$1::EL:EH2:EL::",true));
		r.add(new XMLNoteReplacer("[<]h3[>](.*?)[<][/]h3[>]","::EL:H3:EL::$1::EL:EH3:EL::",true));
		r.add(new XMLNoteReplacer("[<]h3\\s[^>]+[>](.*?)[<][/]h3[>]","::EL:H3:EL::$1::EL:EH3:EL::",true));
		r.add(new XMLNoteReplacer("[<]h4[>](.*?)[<][/]h4[>]","::EL:H4:EL::$1::EL:EH4:EL::",true));
		r.add(new XMLNoteReplacer("[<]h4\\s[^>]+[>](.*?)[<][/]h4[>]","::EL:H4:EL::$1::EL:EH4:EL::",true));
		r.add(new XMLNoteReplacer("[<]h5[>](.*?)[<][/]h5[>]","::EL:B:EL::$1::EL:EB:EL::",true));
		r.add(new XMLNoteReplacer("[<]h5\\s[^>]+[>](.*?)[<][/]h5[>]","::EL:B:EL::$1::EL:EB:EL::",true));
		
		r.add(new XMLNoteReplacer("[<][^>]+[>]","",true,true)); // General tag cleaner.
		
		r.add(new XMLNoteReplacer("::E:BR:E::","<br>",true));
		r.add(new XMLNoteReplacer("::P:PAR:P::(.*?)::P:EPAR:P::","<p>$1</p>",true));
		// If we still have (we don't want that) nested paragraphs, we correct that
		r.add(new XMLNoteReplacer("::P:(EPAR|PAR):P::","",true));
		// For the nested stuff we already have made sure they match, no problem there.
		r.add(new XMLNoteReplacer("::P:UL:P::","<ul>",true));
		r.add(new XMLNoteReplacer("::P:EUL:P::","</ul>",true));
		r.add(new XMLNoteReplacer("::P:OL:P::","<ol>",true));
		r.add(new XMLNoteReplacer("::P:EOL:P::","</ol>",true));
		r.add(new XMLNoteReplacer("::P:LI:P::(.*?)::P:ELI:P::","<li>$1</li>",true));
		r.add(new XMLNoteReplacer("::P:LI:P::","<li>",true));
		r.add(new XMLNoteReplacer("::P:ELI:P::","</li>",true));
		
		// Correct <li>...<ul> to <li>...</li><ul>
		//r.add(new XMLNoteReplacer("[<]"))
		// Now correct nested <li>'s in the html, we can't have nested <li>'s. The outer nesting needs to go.
		r.add(new XMLNoteReplacer("[<]li[>]\\s*[<]([ou])l[>]","<$1l>",true));
		r.add(new XMLNoteReplacer("[<]li[>]([^<]+)[<]([ou])l[>]","<li>$1</li><$2l>",true));
		r.add(new XMLNoteReplacer("[<][/]([ou])l[>]\\s*[<][/]li[>]","</$1l>",true));
		
		
		r.add(new XMLNoteReplacer("[<][/](ul|ol)[>]([^<]+)[<][/]p[>]","</$1><p>$2</p>",true)); // No start paragraph tag for a given paragraph tag.
		 // (other tags have been cleaned with the general tag cleaner).
		 // ul and ol are the only ones left.

		
		r.add(new XMLNoteReplacer("::EL:B:([^:]+):EL::(.*?)::EL:EB:EL::","<b$1>$2</b>",true));
		r.add(new XMLNoteReplacer("::EL:B:EL::(.*?)::EL:EB:EL::","<b>$1</b>",true));
		r.add(new XMLNoteReplacer("::EL:I:EL::(.*?)::EL:EI:EL::","<i>$1</i>",true));
		r.add(new XMLNoteReplacer("::EL:U:EL::(.*?)::EL:EU:EL::","<u>$1</u>",true));
		r.add(new XMLNoteReplacer("::EL:H1:EL::(.*?)::EL:EH1:EL::","<p><b class=\"h1\">$1</b></p>",true));
		r.add(new XMLNoteReplacer("::EL:H2:EL::(.*?)::EL:EH2:EL::","<p><b class=\"h2\">$1</b></p>",true));
		r.add(new XMLNoteReplacer("::EL:H3:EL::(.*?)::EL:EH3:EL::","<p><b class=\"h3\">$1</b></p>",true));
		r.add(new XMLNoteReplacer("::EL:H4:EL::(.*?)::EL:EH4:EL::","<p><b class=\"h4\">$1</b></p>",true));

		
		// correction
		
		r.add(new XMLNoteReplacer("[>]\\s*[<]","><",true,true));  // cleanup empty space between tags, surrounding text
		//r.add(new XMLNoteReplacer("[>]\\s*",">",true));
		r.add(new XMLNoteReplacer("^\\s+","",true));
		r.add(new XMLNoteReplacer("\\s+$","",true));
		
		//r.add(new XMLNoteReplacer("[<]p[>]","<p>$1</p>",true));      // correct html that has no paragraphs at all.
		r.add(new XMLNoteReplacer(new XMLNoteReplacer.Repl() {
			public String process(String in) {
				if (in.substring(0,3).equals("<p>")) {
					return in;
				} else {
					int i1=in.indexOf("<p>");
					int i2=in.indexOf("</p>");
					if (i1<i2) {
						return "<p>"+in.substring(0,i1)+"</p>"+in.substring(i1);
					} else {
						return "<p>"+in;
					}
				}
			}
		})); // correct the html from the beginning to the first <p> tag
		r.add(new XMLNoteReplacer(new XMLNoteReplacer.Repl() {
			public String process(String in) {
				if (in.substring(in.length()-4).equals("</p>")) {
					return in;
				} else {
					int i;
					for(i=in.length()-4;i>=0 && !in.substring(i,i+4).equals("</p>");i--);
					if (i<0) {
						for(i=in.length()-3;i>=0 && !in.substring(i,i+3).equals("<p>");i--);
						if (i<0) {
							return in;
						} else {
							return in+"</p>";
						}
					} else {
						return in.substring(0,i+4)+"<p>"+in.substring(i+4)+"</p>";
					}
				}	
			}
		})); // correct html from end paragraph to end of html
		r.add(new XMLNoteReplacer("[<][/]p[>](.*?)[<]p[>]","</p><p>$1</p><p>",true)); // correct lines in between that have no paragraph boundaries
		r.add(new XMLNoteReplacer("[<]p[>][<][/]p[>]","",true)); // correct empty paragraphs.
		
		
		//This one doesn't work. with </ul></ul></p> 
		//r.add(new XMLNoteReplacer("[<][/](ul|ol)[>](.+?)[<][/]p[>]","</$1><p>$2</p>",true)); // No start paragraph tag for a given paragraph tag.
																							 // (other tags have been cleaned with the general tag cleaner).
																							 // ul and ol are the only ones left.
		// I've moved this cleaner up.

		r.add(new XMLNoteReplacer("(.*)","<p>$1</p>",true));  // enclose html in paragraph tags, in case there were no paragraph tags 
		r.add(new XMLNoteReplacer("[<]p[>][<]p[>]","<p>",true)); // remove double <p><p>
		r.add(new XMLNoteReplacer("[<][/]p[>][<][/]p[>]","</p>",true)); // remove double </p></p>	
		
		r.add(new XMLNoteReplacer("([<]p[>][<][/]p[>])+$","",true)); // remove ending (<p></p>)+
		
		
		r.add(new XMLNoteReplacer(new XMLNoteReplacer.Repl() {
			public String process(String in) {
				return correctNested(in,"[<]li[>]","[<][/]li[>]");
			}
		}));
		
		return r;
	}
	
	private String stripHtml(String str) {

		Vector<XMLNoteReplacer> R=getReplacers();
		Iterator<XMLNoteReplacer> I=R.iterator();
		
		while(I.hasNext()) {
			XMLNoteReplacer r=I.next();
			//System.out.println(r.toString());
			str=r.doReplace(str);
		}
		return str;
	}
	
	static public String strip(String str) {
		if (_stripper==null) {
			_stripper=new HtmlStripper();
		}
		return _stripper.stripHtml(str); 
	}
	
	
}
