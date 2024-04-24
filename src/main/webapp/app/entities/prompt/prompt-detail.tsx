import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './prompt.reducer';

export const PromptDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const promptEntity = useAppSelector(state => state.prompt.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="promptDetailsHeading">
          <Translate contentKey="xploraAdminApp.prompt.detail.title">Prompt</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{promptEntity.id}</dd>
          <dt>
            <span id="key">
              <Translate contentKey="xploraAdminApp.prompt.key">Key</Translate>
            </span>
          </dt>
          <dd>{promptEntity.key}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="xploraAdminApp.prompt.value">Value</Translate>
            </span>
          </dt>
          <dd>{promptEntity.value}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.prompt.language">Language</Translate>
          </dt>
          <dd>{promptEntity.language ? promptEntity.language.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/prompt" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/prompt/${promptEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PromptDetail;
