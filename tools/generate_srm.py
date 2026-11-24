import argparse,re
from pathlib import Path
BASE={'create_by','create_time','update_by','update_time','remark'}
def camel(x): return ''.join(a[:1].upper()+a[1:] for a in x.split('_'))
def prop(x):
 a=x.split('_'); return a[0]+''.join(v[:1].upper()+v[1:] for v in a[1:])
def parse(p):
 ls=Path(p).read_text(encoding='utf-8').splitlines(); out=[]; i=0
 while i<len(ls):
  m=re.match(r'^CREATE TABLE\s+(srm_[a-z0-9_]+)\s*\($',ls[i].strip(),re.I)
  if not m: i+=1; continue
  name=m.group(1); i+=1; body=[]
  while i<len(ls) and not re.match(r'^\)\s*ENGINE=',ls[i].strip(),re.I): body.append(ls[i]); i+=1
  end=ls[i] if i<len(ls) else ''; tm=re.search(r"COMMENT\s*=\s*'([^']*)'",end,re.I); cols=[]
  for line in body:
   r=line.strip(); z=re.match(r'^([a-z][a-z0-9_]*)\s+([a-z]+(?:\([^)]*\))?)(?:\s+(unsigned))?\s+(.*)$',r,re.I)
   if not z: continue
   n,t,u,rest=z.groups(); base=t.split('(',1)[0].upper();
   if base in {'PRIMARY','UNIQUE','KEY','CONSTRAINT','INDEX','FULLTEXT','CHECK'}: continue
   jdbc={'BIGINT':'BIGINT','TINYINT':'TINYINT','SMALLINT':'SMALLINT','MEDIUMINT':'INTEGER','INT':'INTEGER','INTEGER':'INTEGER','DECIMAL':'DECIMAL','NUMERIC':'DECIMAL','DATE':'DATE','DATETIME':'TIMESTAMP','TIMESTAMP':'TIMESTAMP','TIME':'TIME','YEAR':'INTEGER'}.get(base,'VARCHAR')
   jt='Long' if base=='BIGINT' else ('Integer' if base in {'TINYINT','SMALLINT','MEDIUMINT','INT','INTEGER','YEAR'} else ('BigDecimal' if base in {'DECIMAL','NUMERIC'} else ('Date' if base in {'DATE','DATETIME','TIMESTAMP','TIME'} else 'String')))
   q=re.search(r"COMMENT\s+'(.*)'\s*,?$",rest,re.I); comment=q.group(1) if q else ''
   cols.append((n,t.lower()+((' '+u.lower()) if u else ''),jdbc,jt,comment))
  out.append((name,tm.group(1) if tm else '',cols)); i+=1
 if len(out)!=40: raise SystemExit(f'expected 40 tables, got {len(out)}')
 return out
def write_domain(root,t):
 n,tc,cs=t; (root/'ruoyi-srm/src/main/java/com/ruoyi/srm/domain').mkdir(parents=True,exist_ok=True); c=camel(n[4:]); fs=[x for x in cs if x[0] not in BASE]; imports=[]
 if any(x[3]=='BigDecimal' for x in fs): imports+=['import java.math.BigDecimal;']
 if any(x[3]=='Date' for x in cs): imports+=['import java.util.Date;']
 s=['package com.ruoyi.srm.domain;','',*imports,'import com.ruoyi.common.core.domain.BaseEntity;','',f'/** {tc or n} */',f'public class {c} extends BaseEntity','{','    private static final long serialVersionUID = 1L;','']
 for x in fs: s += [f'    /** {x[4]} */',f'    private {x[3]} {prop(x[0])};','']
 for x in fs:
  p=prop(x[0]); cap=p[0].upper()+p[1:]; s += [f'    public {x[3]} get{cap}() {{ return {p}; }}','',f'    public void set{cap}({x[3]} {p}) {{ this.{p} = {p}; }}','']
 s+=['}']; (root/'ruoyi-srm/src/main/java/com/ruoyi/srm/domain'/f'{c}.java').write_text('\n'.join(s)+'\n',encoding='utf-8')
def write_mapper(root,t):
 n,tc,cs=t; c=camel(n[4:]); k=next(x for x in cs if x[0]=='id'); kt=k[3]; p=root/'ruoyi-srm/src/main/java/com/ruoyi/srm/mapper'/f'{c}Mapper.java'; p.parent.mkdir(parents=True,exist_ok=True)
 s=['package com.ruoyi.srm.mapper;','', 'import java.util.List;',f'import com.ruoyi.srm.domain.{c};','',f'public interface {c}Mapper','{',f'    {c} select{c}ById({kt} id);',f'    List<{c}> select{c}List({c} entity);',f'    int insert{c}({c} entity);',f'    int update{c}({c} entity);',f'    int delete{c}ById({kt} id);',f'    int delete{c}ByIds({kt}[] ids);','}']; p.write_text('\n'.join(s)+'\n',encoding='utf-8')
def write_service(root,t):
 n,tc,cs=t; c=camel(n[4:]); k=next(x for x in cs if x[0]=='id'); kt=k[3]; p=root/'ruoyi-srm/src/main/java/com/ruoyi/srm/service'/f'I{c}Service.java'; p.parent.mkdir(parents=True,exist_ok=True)
 s=['package com.ruoyi.srm.service;','', 'import java.util.List;',f'import com.ruoyi.srm.domain.{c};','',f'public interface I{c}Service','{',f'    {c} select{c}ById({kt} id);',f'    List<{c}> select{c}List({c} entity);',f'    int insert{c}({c} entity);',f'    int update{c}({c} entity);',f'    int delete{c}ById({kt} id);',f'    int delete{c}ByIds({kt}[] ids);','}']; p.write_text('\n'.join(s)+'\n',encoding='utf-8')
 p=root/'ruoyi-srm/src/main/java/com/ruoyi/srm/service/impl'/f'{c}ServiceImpl.java'; p.parent.mkdir(parents=True,exist_ok=True); v=prop(c)
 s=['package com.ruoyi.srm.service.impl;','', 'import java.util.List;','import org.springframework.beans.factory.annotation.Autowired;','import org.springframework.stereotype.Service;',f'import com.ruoyi.srm.domain.{c};',f'import com.ruoyi.srm.mapper.{c}Mapper;',f'import com.ruoyi.srm.service.I{c}Service;','', '@Service',f'public class {c}ServiceImpl implements I{c}Service','{','    @Autowired',f'    private {c}Mapper {v}Mapper;','',f'    public {c} select{c}ById({kt} id) {{ return {v}Mapper.select{c}ById(id); }}',f'    public List<{c}> select{c}List({c} entity) {{ return {v}Mapper.select{c}List(entity); }}',f'    public int insert{c}({c} entity) {{ return {v}Mapper.insert{c}(entity); }}',f'    public int update{c}({c} entity) {{ return {v}Mapper.update{c}(entity); }}',f'    public int delete{c}ById({kt} id) {{ return {v}Mapper.delete{c}ById(id); }}',f'    public int delete{c}ByIds({kt}[] ids) {{ return {v}Mapper.delete{c}ByIds(ids); }}','}']; p.write_text('\n'.join(s)+'\n',encoding='utf-8')
def write_xml(root,t):
 n,tc,cs=t; c=camel(n[4:]); k=next(x for x in cs if x[0]=='id'); kp=prop(k[0]); cols=', '.join(x[0] for x in cs); p=root/'ruoyi-srm/src/main/resources/mapper/srm'/f'{c}Mapper.xml'; p.parent.mkdir(parents=True,exist_ok=True)
 s=['<?xml version="1.0" encoding="UTF-8" ?>','<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">',f'<mapper namespace="com.ruoyi.srm.mapper.{c}Mapper">',f'  <resultMap id="{c}Result" type="com.ruoyi.srm.domain.{c}">']
 for x in cs: s.append(f'    <{"id" if x[0]==k[0] else "result"} property="{prop(x[0])}" column="{x[0]}" jdbcType="{x[2]}" />')
 s += ['  </resultMap>',f'  <sql id="select{c}Vo">SELECT {cols} FROM {n}</sql>',f'  <select id="select{c}ById" parameterType="{k[3]}" resultMap="{c}Result"><include refid="select{c}Vo"/> WHERE {k[0]} = #{{{kp}}}</select>',f'  <select id="select{c}List" parameterType="com.ruoyi.srm.domain.{c}" resultMap="{c}Result"><include refid="select{c}Vo"/><where>']
 for x in cs:
  if x[0] in BASE: continue
  q=' != null and '+prop(x[0])+' != \'\'' if x[3]=='String' else ' != null'; s.append(f'    <if test="{prop(x[0])}{q}">AND {x[0]} = #{{{prop(x[0])}}}</if>')
 s += ['  </where></select>',f'  <insert id="insert{c}" parameterType="com.ruoyi.srm.domain.{c}" useGeneratedKeys="true" keyProperty="{kp}">INSERT INTO {n} ({", ".join(x[0] for x in cs if x[0]!=k[0])}) VALUES ({", ".join("#{"+prop(x[0])+"}" for x in cs if x[0]!=k[0])})</insert>',f'  <update id="update{c}" parameterType="com.ruoyi.srm.domain.{c}">UPDATE {n}<set>']
 for x in cs:
  if x[0]!=k[0]: s.append(f'    <if test="{prop(x[0])} != null">{x[0]} = #{{{prop(x[0])}}},</if>')
 s += [f'  </set> WHERE {k[0]} = #{{{kp}}}</update>',f'  <delete id="delete{c}ById" parameterType="{k[3]}">DELETE FROM {n} WHERE {k[0]} = #{{{kp}}}</delete>',f'  <delete id="delete{c}ByIds" parameterType="array">DELETE FROM {n} WHERE {k[0]} IN <foreach collection="array" item="id" open="(" separator="," close=")">#{{id}}</foreach></delete>','</mapper>']; p.write_text('\n'.join(s)+'\n',encoding='utf-8')
def main():
 a=argparse.ArgumentParser(); a.add_argument('--ddl',required=True); a.add_argument('--root',required=True); a.add_argument('--manifest',required=True); x=a.parse_args(); root=Path(x.root); ts=parse(x.ddl)
 for t in ts: write_domain(root,t); write_mapper(root,t); write_service(root,t); write_xml(root,t)
 m=['# SRM DDL manifest','',f'Tables: {len(ts)}',''];
 for n,tc,cs in ts: m += [f'- `{n}`: {len(cs)} columns']+[f'  - `{c[0]}` | `{c[1]}` | {c[4]}' for c in cs]
 q=Path(x.manifest); q.parent.mkdir(parents=True,exist_ok=True); q.write_text('\n'.join(m)+'\n',encoding='utf-8')
if __name__=='__main__': main()


