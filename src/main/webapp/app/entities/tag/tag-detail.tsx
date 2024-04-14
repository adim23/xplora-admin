import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tag.reducer';

export const TagDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tagEntity = useAppSelector(state => state.tag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tagDetailsHeading">
          <Translate contentKey="xploraAdminApp.tag.detail.title">Tag</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tagEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="xploraAdminApp.tag.code">Code</Translate>
            </span>
          </dt>
          <dd>{tagEntity.code}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tag.place">Place</Translate>
          </dt>
          <dd>
            {tagEntity.places
              ? tagEntity.places.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {tagEntity.places && i === tagEntity.places.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tag.tour">Tour</Translate>
          </dt>
          <dd>
            {tagEntity.tours
              ? tagEntity.tours.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {tagEntity.tours && i === tagEntity.tours.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tag.tourExtra">Tour Extra</Translate>
          </dt>
          <dd>
            {tagEntity.tourExtras
              ? tagEntity.tourExtras.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {tagEntity.tourExtras && i === tagEntity.tourExtras.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.tag.webPage">Web Page</Translate>
          </dt>
          <dd>
            {tagEntity.webPages
              ? tagEntity.webPages.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {tagEntity.webPages && i === tagEntity.webPages.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/tag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tag/${tagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TagDetail;
